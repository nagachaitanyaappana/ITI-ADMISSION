package com.server.backend.service.Admission;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.backend.DTO.TradeSelectionRequestDto;

/**
 * Records a candidate's chosen ITI/trade in {@code trade_sel}.
 *
 * <p>Direct port of the legacy Struts action {@code Master.Trade.Tradesel_entry_Action}
 * ({@code src/java/Master/Trade/Tradesel_entry_Action.java:72}). This write path was **never
 * ported** to Spring Boot, which is why {@code trade_sel} is empty and merit generation returns
 * "No records found": the candidate query is
 * {@code regid in (select regid from trade_sel where ...)}, so no selections means no candidates.
 *
 * <p>Reproduced deliberately from the legacy action:
 * <ul>
 *   <li>the hstore phase key is appended with {@code phase || hstore(...)} rather than overwritten,
 *       so earlier phase membership survives;</li>
 *   <li>{@code application.year} / {@code application.phase} are updated in the same step — that
 *       update is the only writer of {@code application.phase} in the merit path, and the
 *       generator's {@code hstore(a.phase)->:phase='true'} predicate depends on it.</li>
 * </ul>
 */
@Service
public class TradeSelectionService {

    private static final Logger logger = LoggerFactory.getLogger(TradeSelectionService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TradeSelectionService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** Resolves the running admission year, as legacy did via iti_params code '7'. */
    public String resolveYear() {
        List<String> years = jdbcTemplate.getJdbcTemplate()
                .queryForList("SELECT value FROM iti_params WHERE code = '7'", String.class);
        if (years.isEmpty() || years.get(0) == null || years.get(0).isBlank()) {
            throw new IllegalStateException("ITI param for code '7' (admission year) not found");
        }
        return years.get(0);
    }

    /**
     * Resolves the current phase.
     *
     * <p>{@code admissions.admission_phase} is the authority, not {@code iti_params}: the
     * param currently reads 2026 while the only current phase row is 2025, so looking the phase
     * up by the param year finds nothing. The param year is still used for the {@code year}
     * column, and a disagreement is logged rather than thrown — this mirrors
     * {@code MeritListService.resolveYear/resolvePhase}.
     */
    public String resolvePhase(String paramYear) {
        List<Map<String, Object>> byParamYear = jdbcTemplate.queryForList(
                "SELECT phase FROM admissions.admission_phase WHERE year = :year AND current = 'true'",
                new MapSqlParameterSource("year", paramYear));
        if (!byParamYear.isEmpty()) {
            return String.valueOf(byParamYear.get(0).get("phase"));
        }

        List<Map<String, Object>> current = jdbcTemplate.getJdbcTemplate().queryForList(
                "SELECT year, phase FROM admissions.admission_phase WHERE current = 'true'");
        if (current.isEmpty()) {
            throw new IllegalStateException(
                    "No current admission phase found in admissions.admission_phase");
        }
        Map<String, Object> row = current.get(0);
        String actualYear = String.valueOf(row.get("year"));
        Object phase = row.get("phase");
        if (phase == null || String.valueOf(phase).isBlank()) {
            throw new IllegalStateException("Current admission phase is null for year " + actualYear);
        }
        logger.warn("iti_params code '7' year {} has no current admission_phase row; using the "
                + "current phase (year {}) instead", paramYear, actualYear);
        return String.valueOf(phase);
    }

    /**
     * Records (or re-records) a trade selection and moves the candidate into the current phase.
     *
     * @param scopeItiCode ITI code for the selection; must be non-blank
     * @param scopeDistCode district code, stored for traceability; the merit query filters on
     *                      {@code iti_code} for role 4 and {@code dist_code} for role 3
     * @param freezee 1 to freeze the preference immediately, null/0 to leave it unset
     * @return a small map describing what was written
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveSelection(TradeSelectionRequestDto dto, String scopeItiCode,
            String scopeDistCode, Integer freezee) {

        if (dto == null || dto.getRegid() == null) {
            throw new IllegalArgumentException("regid is required");
        }
        if (scopeItiCode == null || scopeItiCode.isBlank()) {
            throw new IllegalArgumentException("itiCode is required to record a trade selection");
        }

        String year = resolveYear();
        String phase = resolvePhase(year);
        String regid = String.valueOf(dto.getRegid());
        String itiCode = scopeItiCode.trim();
        // trade_sel.dist_code is nullable, but filling it lets a district-scoped merit run
        // (role 3) find the same rows.
        String distCode = (scopeDistCode == null || scopeDistCode.isBlank())
                ? null : scopeDistCode.trim();
        Integer freeze = (freezee == null) ? 0 : freezee;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("regid", regid)
                .addValue("itiCode", itiCode)
                .addValue("distCode", distCode)
                .addValue("freezee", freeze)
                .addValue("phase", phase)
                .addValue("year", year);

        // Primary key is (iti_code, regid), so this is an upsert. An existing row keeps its
        // history: the phase key is appended, matching legacy "update ... phase || hstore(...)".
        // `temp` is NOT NULL with no column default on this schema and the legacy inserts never
        // supplied it either (they relied on a production-side default that is absent here), so
        // it is taken explicitly from the existing public.trade_sel_temp_seq.
        String upsert = """
                INSERT INTO trade_sel(regid, iti_code, dist_code, freezee, temp_code, temp, phase, year)
                VALUES (:regid, :itiCode, :distCode, :freezee, :itiCode,
                        nextval('public.trade_sel_temp_seq'),
                        hstore(array[:phase], array['true']), :year)
                ON CONFLICT (iti_code, regid) DO UPDATE SET
                    dist_code = COALESCE(EXCLUDED.dist_code, trade_sel.dist_code),
                    freezee  = EXCLUDED.freezee,
                    year     = EXCLUDED.year,
                    phase    = COALESCE(trade_sel.phase, ''::hstore) || EXCLUDED.phase
                """;
        jdbcTemplate.update(upsert, params);

        // The only writer of application.phase in the merit path. The COALESCE-to-'' guard
        // matters: a NULL phase cannot be concatenated with ||.
        String applicationUpdate = """
                UPDATE application
                   SET year = :year,
                       phase = COALESCE(phase, ''::hstore) || hstore(array[:phase], array['true'])
                 WHERE regid = :regid
                """;
        int updated = jdbcTemplate.update(applicationUpdate,
                new MapSqlParameterSource("regid", dto.getRegid())
                        .addValue("year", year)
                        .addValue("phase", phase));

        if (updated == 0) {
            logger.warn("Trade selection saved for regid {} but no application row matched", regid);
        }

        return Map.of(
                "success", true,
                "regid", regid,
                "itiCode", itiCode,
                "distCode", distCode == null ? "" : distCode,
                "phase", phase,
                "year", year,
                "freezee", freeze,
                "applicationUpdated", updated);
    }

    /**
     * Removes a candidate's phase membership from {@code trade_sel} — the legacy delete branch
     * ({@code UPDATE trade_sel SET phase = delete(phase, array[?]) WHERE regid=? AND iti_code=?}).
     * The row itself is kept so the selection can be re-confirmed without a fresh insert.
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> removeSelection(Integer regid, String itiCode) {
        if (regid == null) {
            throw new IllegalArgumentException("regid is required");
        }
        if (itiCode == null || itiCode.isBlank()) {
            throw new IllegalArgumentException("itiCode is required");
        }
        String year = resolveYear();
        String phase = resolvePhase(year);

        int rows = jdbcTemplate.update(
                "UPDATE trade_sel SET phase = delete(phase, array[:phase]) "
                        + "WHERE regid = :regid AND iti_code = :itiCode",
                new MapSqlParameterSource("regid", String.valueOf(regid))
                        .addValue("itiCode", itiCode.trim())
                        .addValue("phase", phase));

        return Map.of(
                "success", true,
                "regid", String.valueOf(regid),
                "itiCode", itiCode.trim(),
                "phase", phase,
                "rowsAffected", rows);
    }

    /** Lists a candidate's current selections, for the trade-selection page. */
    public List<Map<String, Object>> getSelections(Integer regid) {
        if (regid == null) {
            throw new IllegalArgumentException("regid is required");
        }
        return jdbcTemplate.queryForList(
                "SELECT regid, iti_code, dist_code, freezee, temp_code, year, phase "
                        + "FROM trade_sel WHERE regid = :regid ORDER BY iti_code",
                new MapSqlParameterSource("regid", String.valueOf(regid)));
    }
}