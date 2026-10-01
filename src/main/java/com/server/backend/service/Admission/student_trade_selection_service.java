package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.backend.DTO.student_trade_selection_dto;
import com.server.backend.DTO.student_trade_selection_request_dto;
import com.server.backend.service.ITI.TradeSelectionService;

/**
 * Candidate-side write path into {@code student_trade_sel}.
 *
 * <p>Direct port of the insert in {@code applicationForm.open_Application_InterfaceAction}
 * ({@code src/java/applicationForm/open_Application_InterfaceAction.java:321}), the action behind
 * {@code web/open_Application_Interface.jsp} and its {@code locDeptTable}. Without it the table
 * stays empty, so nothing can ever be promoted into {@code trade_sel} by the district/ITI
 * verifier ({@code Re_Verification_Action:572}, {@code verification_interfaceAction:586}) and
 * merit generation finds no candidate.
 *
 * <p>Column semantics taken from the legacy statement and confirmed against the archived phase
 * snapshot ({@code public.trade_sel2025phase1}, 22,060 rows):
 * <ul>
 *   <li>{@code dist_code} — the district of the chosen ITI;</li>
 *   <li>{@code temp_code} — legacy passes the district code again (the archive has zero rows
 *       where {@code temp_code <> dist_code});</li>
 *   <li>{@code iti_code} — the chosen ITI, so the row is the choice;</li>
 *   <li>{@code phase} — {@code hstore(<phase>, 'true')};</li>
 *   <li>{@code year} — {@code iti_params} code '7';</li>
 *   <li>{@code trno} — audit trace number (prod takes it from {@code trno_seq}, whose last_value
 *       sits just above the archived {@code trno} values);</li>
 *   <li>{@code temp} — surrogate that the legacy INSERT omits and lets the column default fill.
 *       This schema lost that default, so the port supplies {@code nextval('temp_seq')} explicitly
 *       (the only sequence whose range matches the archived {@code temp} values). Nothing reads the
 *       column back.</li>
 * </ul>
 *
 * <p>Deliberate deviation from legacy: {@code open_Application_InterfaceAction} only ever appended,
 * so re-submitting would hit the primary key {@code (iti_code, regid)} and abort the form post —
 * which is why the legacy edit screens re-render saved rows instead of letting a candidate
 * re-save. The port replaces the candidate's rows inside one transaction, making the save
 * idempotent.
 */
@Service
public class student_trade_selection_service {

    /** Legacy caps the selection table at 60 rows ({@code open_Application_Interface.jsp}). */
    public static final int MAX_CHOICES = 60;

    private static final Logger logger =
            LoggerFactory.getLogger(student_trade_selection_service.class);

    /** Codes only in {@code student_trade_sel}; names come from {@code iti} / {@code dist_mst}. */
    private static final String SELECT_SQL = """
            SELECT s.regid,
                   s.dist_code,
                   d.dist_name,
                   s.iti_code,
                   i.iti_name,
                   i.govt,
                   s.temp_code,
                   s.trno,
                   s.year,
                   s.phase::text AS phase_text,
                   s.freezee
              FROM student_trade_sel s
              LEFT JOIN iti i      ON i.iti_code  = s.iti_code
              LEFT JOIN dist_mst d ON d.dist_code = s.dist_code
             WHERE s.regid = :regid
             ORDER BY d.dist_name NULLS LAST, i.iti_name NULLS LAST, s.iti_code
            """;

    /**
     * The legacy insert, with {@code temp} made explicit.
     *
     * <p>{@code hstore(ARRAY[..], ARRAY[..])} is used rather than {@code hstore(:phase,'true')}
     * because a JDBC bind parameter has no inferred type inside the two-argument form.
     */
    private static final String INSERT_SQL = """
            INSERT INTO student_trade_sel
                        (regid, dist_code, temp_code, trno, phase, year, iti_code, temp, freezee)
            VALUES      (:regid, :distCode, :distCode, :trno,
                         hstore(ARRAY[:phase], ARRAY['true']), :year, :itiCode,
                         nextval('temp_seq'), :freezee)
            """;

    private static final RowMapper<student_trade_selection_dto> ROW_MAPPER =
            (rs, rowNum) -> new student_trade_selection_dto(
                    rs.getInt("regid"),
                    rs.getString("dist_code"),
                    rs.getString("dist_name"),
                    rs.getString("iti_code"),
                    rs.getString("iti_name"),
                    rs.getString("govt"),
                    rs.getString("temp_code"),
                    rs.getObject("trno", Long.class),
                    rs.getString("year"),
                    rs.getString("phase_text"),
                    rs.getObject("freezee", Integer.class));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Reused so this half of the flow resolves year and phase exactly like the {@code trade_sel}
     * half — otherwise the candidate's row would be tagged with a phase the verifier never reads.
     */
    private final TradeSelectionService tradeSelectionService;

    public student_trade_selection_service(NamedParameterJdbcTemplate jdbcTemplate,
            TradeSelectionService tradeSelectionService) {
        this.jdbcTemplate = jdbcTemplate;
        this.tradeSelectionService = tradeSelectionService;
    }

    /** Lists the candidate's saved choices with the district/ITI names the page displays. */
    @Transactional(readOnly = true)
    public List<student_trade_selection_dto> getSelections(Integer regid) {
        requireRegid(regid);
        return jdbcTemplate.query(SELECT_SQL,
                new MapSqlParameterSource("regid", regid), ROW_MAPPER);
    }

    /**
     * Replaces the candidate's choices with the posted list.
     *
     * <p>Order of operations (all in one transaction): validate the candidate and every ITI,
     * delete the candidate's existing rows, insert the new ones.
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveSelections(Integer regId,
            student_trade_selection_request_dto request) {

        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        Integer regid = request.getRegid() == null ? regId : request.getRegid();
        requireRegid(regid);
        if (regId != null && request.getRegid() != null && !regId.equals(request.getRegid())) {
            throw new IllegalArgumentException("regid in the body (" + request.getRegid()
                    + ") does not match the path (" + regId + ")");
        }

        List<student_trade_selection_request_dto.Choice> choices = request.getChoices();
        if (choices == null || choices.isEmpty()) {
            throw new IllegalArgumentException("Select at least one ITI");
        }
        if (choices.size() > MAX_CHOICES) {
            throw new IllegalArgumentException("A maximum of " + MAX_CHOICES
                    + " ITIs can be selected (received " + choices.size() + ")");
        }

        requireCandidateExists(regid);
        Set<String> itiCodes = collectItiCodes(choices);
        Map<String, String> districtByIti = resolveDistricts(choices);

        String year = request.getYear() == null || request.getYear().isBlank()
                ? tradeSelectionService.resolveYear() : request.getYear().trim();
        String phase = request.getPhase() == null || request.getPhase().isBlank()
                ? tradeSelectionService.resolvePhase(year) : request.getPhase().trim();

        // Replace, so a re-submission is idempotent (see the class javadoc).
        int cleared = jdbcTemplate.update("DELETE FROM student_trade_sel WHERE regid = :regid",
                new MapSqlParameterSource("regid", regid));

        insertChoices(regid, year, phase, districtByIti, request.getFreezee());

        logger.info("Saved {} ITI selection(s) for regid {} (year {}, phase {}, {} replaced)",
                districtByIti.size(), regid, year, phase, cleared);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", true);
        result.put("regid", regid);
        result.put("year", year);
        result.put("phase", phase);
        result.put("cleared", cleared);
        result.put("saved", districtByIti.size());
        result.put("itiCodes", new ArrayList<>(itiCodes));
        return result;
    }

    /** The candidate must exist: {@code student_application} is what the student flow writes. */
    private void requireCandidateExists(Integer regid) {
        List<Integer> found = jdbcTemplate.getJdbcTemplate().queryForList(
                "SELECT regid FROM student_application WHERE regid = ?", Integer.class, regid);
        if (found.isEmpty()) {
            throw new IllegalArgumentException("No registration found for Registration Id " + regid
                    + ". Please register and apply first.");
        }
    }

    /** Trims the codes and rejects blanks and duplicates — the PK is {@code (iti_code, regid)}. */
    private static Set<String> collectItiCodes(
            List<student_trade_selection_request_dto.Choice> choices) {

        Set<String> itiCodes = new LinkedHashSet<>();
        for (student_trade_selection_request_dto.Choice choice : choices) {
            if (choice == null || choice.getItiCode() == null || choice.getItiCode().isBlank()) {
                throw new IllegalArgumentException("Every row must have an ITI selected");
            }
            String itiCode = choice.getItiCode().trim();
            if (!itiCodes.add(itiCode)) {
                throw new IllegalArgumentException("ITI " + itiCode + " is selected more than once");
            }
        }
        return itiCodes;
    }

    /**
     * Resolves each chosen ITI's district from {@code iti} — authoritative over the posted code, so
     * a stale or hand-crafted {@code distCode} can never create a row whose district disagrees with
     * the ITI's real district.
     *
     * @return {@code itiCode -> dist_code}, in the order the candidate posted them
     */
    private Map<String, String> resolveDistricts(
            List<student_trade_selection_request_dto.Choice> choices) {

        Set<String> itiCodes = new LinkedHashSet<>();
        for (student_trade_selection_request_dto.Choice choice : choices) {
            itiCodes.add(choice.getItiCode().trim());
        }

        Map<String, String> districtByIti = new LinkedHashMap<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(
                "SELECT iti_code, dist_code FROM iti WHERE iti_code IN (:codes)",
                new MapSqlParameterSource("codes", itiCodes))) {
            districtByIti.put(String.valueOf(row.get("iti_code")),
                    row.get("dist_code") == null ? null : String.valueOf(row.get("dist_code")));
        }

        Map<String, String> resolved = new LinkedHashMap<>();
        for (student_trade_selection_request_dto.Choice choice : choices) {
            String itiCode = choice.getItiCode().trim();
            String actual = districtByIti.get(itiCode);
            if (actual == null) {
                throw new IllegalArgumentException("Unknown ITI code " + itiCode);
            }
            String posted = choice.getDistCode() == null ? null : choice.getDistCode().trim();
            if (posted != null && !posted.isEmpty() && !posted.equals(actual)) {
                throw new IllegalArgumentException("ITI " + itiCode + " belongs to district "
                        + actual + ", not " + posted);
            }
            resolved.put(itiCode, actual);
        }
        return resolved;
    }

    /**
     * Writes one row per choice. {@code temp_code} repeats the district and {@code temp} takes the
     * next {@code temp_seq} value, matching how the archived rows were produced.
     */
    private void insertChoices(Integer regid, String year, String phase,
            Map<String, String> districtByIti, Integer freezee) {

        Long trno = jdbcTemplate.getJdbcTemplate()
                .queryForObject("SELECT nextval('trno_seq')", Long.class);

        List<SqlParameterSource> batch = new ArrayList<>(districtByIti.size());
        for (Map.Entry<String, String> entry : districtByIti.entrySet()) {
            batch.add(new MapSqlParameterSource("regid", regid)
                    .addValue("distCode", entry.getValue())
                    .addValue("trno", trno)
                    .addValue("phase", phase)
                    .addValue("year", year)
                    .addValue("itiCode", entry.getKey())
                    .addValue("freezee", freezee));
        }
        jdbcTemplate.batchUpdate(INSERT_SQL, batch.toArray(new SqlParameterSource[0]));
    }

    /** Removes a single choice — the legacy per-row Delete link. */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> removeSelection(Integer regid, String itiCode) {
        requireRegid(regid);
        if (itiCode == null || itiCode.isBlank()) {
            throw new IllegalArgumentException("itiCode is required");
        }

        int rows = jdbcTemplate.update(
                "DELETE FROM student_trade_sel WHERE regid = :regid AND iti_code = :itiCode",
                new MapSqlParameterSource("regid", regid).addValue("itiCode", itiCode.trim()));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", true);
        result.put("regid", regid);
        result.put("itiCode", itiCode.trim());
        result.put("rowsAffected", rows);
        return result;
    }

    /** Clears every choice for the candidate. */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> clearSelections(Integer regid) {
        requireRegid(regid);

        int rows = jdbcTemplate.update("DELETE FROM student_trade_sel WHERE regid = :regid",
                new MapSqlParameterSource("regid", regid));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("success", true);
        result.put("regid", regid);
        result.put("rowsAffected", rows);
        return result;
    }

    private static void requireRegid(Integer regid) {
        if (regid == null || regid <= 0) {
            throw new IllegalArgumentException("A valid Registration Id is required");
        }
    }
}
