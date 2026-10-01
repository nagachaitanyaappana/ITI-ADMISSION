package com.server.backend.service.MeritChecklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.backend.DTO.TakeAdmissionRequest;
import com.server.backend.DTO.UserPrincipal;

/**
 * Backend for the Admission Phase 1 page ({@code /AdmissionPhase}).
 *
 * <p>That page used to call endpoints which did not exist (404) or which were a different
 * endpoint entirely ({@code POST /admission-timings} is the "create a schedule row" route, so
 * every call the page made returned 400 and the page silently fell back to its console). This
 * service implements the four contracts the page actually builds:
 *
 * <ul>
 *   <li>{@code GET  /api/admission/iti-boards} — "Type of Admission" {@code <select>}</li>
 *   <li>{@code POST /api/admission/iti-seat-matrix} — the T/F/V grid per ITI + trade</li>
 *   <li>{@code POST /api/admission/rank-lookup} — merit rank → candidate profile</li>
 *   <li>{@code POST /api/admission/take-admission} — the write that admits the candidate</li>
 * </ul>
 *
 * <p>Two pieces of published domain configuration drive everything and are easy to get wrong:
 * <ul>
 *   <li>{@code admissions.admission_phase} (row flagged {@code current}) is the authority for the
 *       running year and phase — not {@code iti_params} code '7', which reads 2026 while the only
 *       current phase row is 2025.</li>
 *   <li>the seat matrix is published for {@code seatmatrix_phase}, not for the admission phase: the
 *       current row is phase 1 with {@code seatmatrix_phase} 5, and the seats for 2025 only exist
 *       under phase 5. Using the admission phase silently yields an empty matrix.</li>
 * </ul>
 */
@Service
public class AdmissionProcessService {

    private static final Logger logger = LoggerFactory.getLogger(AdmissionProcessService.class);

    /** Merit ranks are 1-based; table names are built from the year/phase, so they are validated. */
    private static final String NUMERIC = "^[0-9]+$";

    private final NamedParameterJdbcTemplate jdbc;

    public AdmissionProcessService(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // ------------------------------------------------------------------ phase config

    /** The running year / phase / seat-matrix phase, straight from admissions.admission_phase. */
    private Map<String, Object> currentPhaseConfig() {
        List<Map<String, Object>> rows = jdbc.getJdbcTemplate().queryForList(
                "SELECT year, phase, seatmatrix_phase FROM admissions.admission_phase WHERE current = 'true'");
        if (rows.isEmpty()) {
            throw new IllegalStateException(
                    "No current admission phase found in admissions.admission_phase");
        }
        return rows.get(0);
    }

    private String currentYear(Map<String, Object> config) {
        String year = text(config.get("year"));
        if (blank(year)) {
            throw new IllegalStateException("Current admission phase has no year");
        }
        return year;
    }

    private int currentPhase(Map<String, Object> config) {
        return number(config.get("phase"), 1);
    }

    /**
     * Seats are published under their own phase. Falling back to the admission phase would make
     * every ITI show "no seats" for the 2025 data set, where the matrix sits under phase 5.
     */
    private int currentSeatMatrixPhase(Map<String, Object> config) {
        Object seatMatrixPhase = config.get("seatmatrix_phase");
        int admissionPhase = currentPhase(config);
        return seatMatrixPhase == null ? admissionPhase : number(seatMatrixPhase, admissionPhase);
    }

    // ------------------------------------------------------------------ boards

    /** Populates the page's "Type of Admission" dropdown. */
    public Map<String, Object> getItiBoards() {
        List<Map<String, Object>> rows = jdbc.getJdbcTemplate().queryForList(
                "SELECT board_code, board_name FROM admissions.iti_board "
                        + "WHERE status = 'y' ORDER BY board_code");

        List<Map<String, Object>> data = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("board_code", text(row.get("board_code")));
            item.put("board_name", text(row.get("board_name")));
            data.add(item);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        return response;
    }

    // ------------------------------------------------------------------ seat matrix

    /**
     * The page renders four category columns (General, IM, PH, EX-S) with T(otal)/F(illed)/V(acant)
     * each. {@code iti_seatmatrix} stores those numbers in three hstore columns keyed by caste code,
     * so the mapping lives here, once:
     *
     * <ul>
     *   <li>General ← {@code OC} (+ women's {@code OC-W}); PH ← {@code PH} (+ {@code PH-W});
     *       EX-S ← {@code EX-S} (+ {@code EX-SW})</li>
     *   <li>IM ← {@code IM}, falling back to {@code IMC} which older rows (2015) still use</li>
     *   <li>{@code strength_total} is every bucket summed, i.e. the sanctioned intake for the trade</li>
     * </ul>
     *
     * <p>The women's buckets are added into their parent category: the page has no women's column,
     * so leaving them out would hide real sanctioned seats.
     */
    private static final String SEAT_MATRIX_SQL = """
            SELECT sm.iti_code,
                   i.iti_name,
                   sm.trade_code,
                   COALESCE(m.trade_name, m.trade_short, sm.trade_code::text) AS trade_name,
                   COALESCE(tot.total, 0) AS strength_total,
                   COALESCE(sm.strength -> 'OC', '0')::int + COALESCE(sm.strength -> 'OC-W', '0')::int AS gen_t,
                   COALESCE(sm.strength_fill -> 'OC', '0')::int + COALESCE(sm.strength_fill -> 'OC-W', '0')::int AS gen_f,
                   COALESCE(sm.strength_vacant -> 'OC', '0')::int + COALESCE(sm.strength_vacant -> 'OC-W', '0')::int AS gen_v,
                   COALESCE(sm.strength -> 'IM', sm.strength -> 'IMC', '0')::int AS im_t,
                   COALESCE(sm.strength_fill -> 'IM', sm.strength_fill -> 'IMC', '0')::int AS im_f,
                   COALESCE(sm.strength_vacant -> 'IM', sm.strength_vacant -> 'IMC', '0')::int AS im_v,
                   COALESCE(sm.strength -> 'PH', '0')::int + COALESCE(sm.strength -> 'PH-W', '0')::int AS ph_t,
                   COALESCE(sm.strength_fill -> 'PH', '0')::int + COALESCE(sm.strength_fill -> 'PH-W', '0')::int AS ph_f,
                   COALESCE(sm.strength_vacant -> 'PH', '0')::int + COALESCE(sm.strength_vacant -> 'PH-W', '0')::int AS ph_v,
                   COALESCE(sm.strength -> 'EX-S', '0')::int + COALESCE(sm.strength -> 'EX-SW', '0')::int AS exs_t,
                   COALESCE(sm.strength_fill -> 'EX-S', '0')::int + COALESCE(sm.strength_fill -> 'EX-SW', '0')::int AS exs_f,
                   COALESCE(sm.strength_vacant -> 'EX-S', '0')::int + COALESCE(sm.strength_vacant -> 'EX-SW', '0')::int AS exs_v
            FROM public.iti_seatmatrix sm
            JOIN public.iti i ON i.iti_code = sm.iti_code
            LEFT JOIN public.ititrade_master m ON m.trade_code = sm.trade_code
            LEFT JOIN LATERAL (
                SELECT SUM(v::int) AS total FROM (SELECT svals(sm.strength) AS v) AS bucket
            ) tot ON TRUE
            WHERE sm.year = :year
              AND sm.phase = :seatmatrixPhase
              AND (CAST(:itiCode AS text) IS NULL OR sm.iti_code = CAST(:itiCode AS text))
              AND (CAST(:distCode AS text) IS NULL OR i.dist_code = CAST(:distCode AS text))
            ORDER BY i.iti_name, trade_name
            """;

    /**
     * Seat availability for the logged-in scope. A role 4 (ITI) login sees its own trades, a role 3
     * (district) login sees every ITI in the district.
     */
    public Map<String, Object> getSeatMatrix(UserPrincipal user) {
        Map<String, Object> config = currentPhaseConfig();
        String year = currentYear(config);
        int seatMatrixPhase = currentSeatMatrixPhase(config);

        String itiCode = blank(user.itiCode()) ? null : user.itiCode().trim();
        String distCode = blank(user.distCode()) ? null : user.distCode().trim();
        if (itiCode == null && distCode == null) {
            throw new IllegalArgumentException(
                    "No ITI or district code is available for this login; cannot scope the seat matrix");
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("year", year)
                .addValue("seatmatrixPhase", seatMatrixPhase)
                .addValue("itiCode", itiCode)
                .addValue("distCode", distCode);

        List<Map<String, Object>> rows = jdbc.queryForList(SEAT_MATRIX_SQL, params);

        List<Map<String, Object>> data = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("iti_code", text(row.get("iti_code")));
            item.put("iti_name", text(row.get("iti_name")));
            item.put("trade_code", row.get("trade_code"));
            item.put("trade_name", text(row.get("trade_name")));
            item.put("strength_total", number(row.get("strength_total"), 0));

            Map<String, Object> categories = new LinkedHashMap<>();
            categories.put("General", tfv(row, "gen_t", "gen_f", "gen_v"));
            categories.put("IM", tfv(row, "im_t", "im_f", "im_v"));
            categories.put("PH", tfv(row, "ph_t", "ph_f", "ph_v"));
            categories.put("EXS", tfv(row, "exs_t", "exs_f", "exs_v"));
            item.put("categories", categories);

            data.add(item);
        }

        if (data.isEmpty()) {
            logger.warn("Seat matrix is empty for year {} at seat-matrix phase {} (scope: iti={}, dist={})",
                    year, seatMatrixPhase, itiCode, distCode);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("year", year);
        response.put("phase", seatMatrixPhase);
        response.put("data", data);
        return response;
    }

    // ------------------------------------------------------------------ rank lookup

    /**
     * Merit rank → candidate profile, which is what {@code handleRankSubmit()} posts as
     * {@code {"rank": "12"}}.
     *
     * <p>A rank alone is not a key: {@code ranks2025phase1} holds 22,060 rows for 1,663 distinct
     * ranks (four copies each, one per stream) and repeats across districts, so the district of the
     * logged-in ITI is part of the lookup. Without it the page would show another district's
     * candidate for the same rank.
     */
    public Map<String, Object> lookupRank(String rank, UserPrincipal user) {
        if (blank(rank) || !rank.trim().matches(NUMERIC)) {
            throw new IllegalArgumentException("A numeric merit rank is required");
        }
        String wantedRank = rank.trim();

        Map<String, Object> config = currentPhaseConfig();
        String year = currentYear(config);
        int phase = currentPhase(config);

        String itiCode = blank(user.itiCode()) ? null : user.itiCode().trim();
        String distCode = blank(user.distCode()) ? null : user.distCode().trim();
        if (distCode == null) {
            // An ITI login sends only its own code; the district comes from the ITI master.
            distCode = districtOf(itiCode);
        }
        if (distCode == null) {
            throw new IllegalArgumentException(
                    "No district code is available for this login; cannot resolve the rank");
        }

        String rankTable = resolveRankTable(year, phase);
        List<Map<String, Object>> rankRows = jdbc.queryForList(
                "SELECT DISTINCT r.regid AS regid, r.rank AS rank, r.qual AS qual FROM public."
                        + rankTable + " r WHERE r.rank::text = :rank AND r.dist_code = :distCode LIMIT 1",
                new MapSqlParameterSource()
                        .addValue("rank", wantedRank)
                        .addValue("distCode", distCode));
        if (rankRows.isEmpty()) {
            return notFound("No candidate holds rank " + wantedRank + " in district " + distCode
                    + " for year " + year + " phase " + phase);
        }

        String regid = text(rankRows.get(0).get("regid"));
        String candidateTable = resolveCandidateTable(year);
        List<Map<String, Object>> candidates = jdbc.queryForList(
                "SELECT regid, name, fname, caste, gender, dob, phc, exservice, ssc_regno, ssc_year "
                        + "FROM public." + candidateTable + " WHERE regid = CAST(:regid AS integer) LIMIT 1",
                new MapSqlParameterSource("regid", regid));
        if (candidates.isEmpty()) {
            return notFound("Rank " + wantedRank + " is held by registration " + regid
                    + ", which has no application row in " + candidateTable);
        }
        Map<String, Object> candidate = candidates.get(0);

        Map<String, Object> admission = findAdmission(regid);

        Map<String, Object> student = new LinkedHashMap<>();
        student.put("regid", regid);
        student.put("rank", number(rankRows.get(0).get("rank"), 0));
        student.put("name", text(candidate.get("name")));
        student.put("fname", text(candidate.get("fname")));
        student.put("caste", text(candidate.get("caste")));
        student.put("gender", text(candidate.get("gender")));
        student.put("dob", candidate.get("dob"));
        student.put("phc", candidate.get("phc"));
        student.put("exservice", candidate.get("exservice"));
        student.put("year_of_admission", year + "-" + academicYearSuffix(year));
        student.put("ssc_regno", text(candidate.get("ssc_regno")));
        student.put("ssc_year", text(candidate.get("ssc_year")));
        student.put("photo_base64", photoBase64(regid));
        student.put("is_admitted", admission != null);
        student.put("admission_details", admission);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", student);
        return response;
    }

    /** A failed lookup is a normal outcome for the page (it shows the message), not an HTTP error. */
    private static Map<String, Object> notFound(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }

    /**
     * Ranks live in a per-year, per-phase table ({@code ranks2025phase1}). A missing table is
     * reported rather than substituted with another year's ranks, which would hand out the wrong
     * candidate.
     */
    private String resolveRankTable(String year, int phase) {
        String table = "ranks" + year + "phase" + phase;
        if (!tableExists(table)) {
            throw new IllegalStateException("No rank table " + table + " exists for year " + year
                    + " phase " + phase + "; generate the merit list for this phase first");
        }
        return table;
    }

    /** Applications are year-scoped when the suffixed table exists ({@code application2025}). */
    private String resolveCandidateTable(String year) {
        String suffixed = "application" + year;
        return tableExists(suffixed) ? suffixed : "application";
    }

    private boolean tableExists(String table) {
        if (!table.matches("[A-Za-z0-9_]+")) {
            throw new IllegalArgumentException("Unsafe table name: " + table);
        }
        Integer count = jdbc.getJdbcTemplate().queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = ?",
                Integer.class, table);
        return count != null && count > 0;
    }

    /** {@code "2025"} → {@code "26"}, so the page can print the 2025-26 academic year. */
    private static String academicYearSuffix(String year) {
        int value;
        try {
            value = Integer.parseInt(year);
        } catch (NumberFormatException ex) {
            return year;
        }
        return String.format("%02d", (value + 1) % 100);
    }

    /** The admission already recorded for this candidate, or null. */
    private Map<String, Object> findAdmission(String regid) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT ia.adm_num, ia.iti_code, ia.trade_code, ia.date_of_admission, "
                        + "       i.iti_name, COALESCE(m.trade_name, m.trade_short) AS trade_name "
                        + "FROM admissions.iti_admissions ia "
                        + "LEFT JOIN public.iti i ON i.iti_code = ia.iti_code "
                        + "LEFT JOIN public.ititrade_master m ON m.trade_code = ia.trade_code "
                        + "WHERE ia.regid = :regid "
                        + "ORDER BY ia.date_of_admission DESC NULLS LAST LIMIT 1",
                new MapSqlParameterSource("regid", regid));
        if (rows.isEmpty()) {
            return null;
        }
        Map<String, Object> row = rows.get(0);
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("adm_num", text(row.get("adm_num")));
        details.put("iti_code", text(row.get("iti_code")));
        details.put("iti_name", text(row.get("iti_name")));
        details.put("trade_code", row.get("trade_code"));
        details.put("trade_name", text(row.get("trade_name")));
        details.put("date_of_admission", row.get("date_of_admission"));
        return details;
    }

    /** The candidate's photograph, base64 (newlines stripped) or null when none was uploaded. */
    private String photoBase64(String regid) {
        List<String> photos = jdbc.queryForList(
                "SELECT translate(encode(pic, 'base64'), E'\\n', '') FROM public.img_cand_photos "
                        + "WHERE regid = CAST(:regid AS integer) LIMIT 1",
                new MapSqlParameterSource("regid", regid), String.class);
        if (photos.isEmpty() || photos.get(0) == null) {
            return null;
        }
        return photos.get(0).replace("\n", "").replace("\r", "");
    }

    // ------------------------------------------------------------------ take admission

    /** Legacy value carried by every existing row; kept so new rows match the population. */
    private static final String DEFAULT_MODE_OF_ADMISSION = "Convenor of the Sellection comitte";

    private static final String INSERT_ADMISSION_SQL = """
            INSERT INTO admissions.iti_admissions
                (adm_num, regid, name, fname, addr, mname, phno, gender, caste, dob, phc, exservice,
                 iti_code, dist_code, trade_code, year_of_admission, phase, date_of_admission,
                 type_admission, idmarks1, idmarks2, ssc_regno, ssc_board, ssc_year, ssc_month, mode_adm)
            SELECT :admNum,
                   c.regid::text,
                   c.name, c.fname, c.addr, c.mname, c.phno, c.gender, c.caste, c.dob, c.phc, c.exservice,
                   :itiCode, :distCode, :tradeCode, :year, :phase, CURRENT_DATE,
                   :typeAdmission, :idmarks1, :idmarks2,
                   COALESCE(NULLIF(:sscRegno, ''), c.ssc_regno),
                   COALESCE(NULLIF(:sscBoard, ''), c.ssc_board),
                   COALESCE(NULLIF(:sscYear,  ''), c.ssc_year),
                   COALESCE(NULLIF(:sscMonth, ''), c.ssc_month),
                   :modeAdmission
            FROM public.%s c
            WHERE c.regid = CAST(:regid AS integer)
            ORDER BY c.regid
            LIMIT 1
            """;

    /**
     * Records the admission.
     *
     * <p>Semantics decoded from the existing rows: {@code adm_num} is
     * {@code <iti_code><trade_short><yy><3-digit serial>} — {@code 1536FI25011} is Fitter
     * ({@code trade_short = FI}) at ITI 1536, year 2025, serial 11 — so the next number continues
     * that series for the same ITI/trade/year. The seat fill is written to the seat-matrix row the
     * page just rendered, so the grid moves on reload.
     *
     * <p>The candidate row is taken with {@code LIMIT 1}: an application table holds one row per
     * phase per candidate (2025 has 128,836 rows for 64,418 registrations) and those rows carry an
     * identical profile, so reading them all used to insert the same {@code adm_num} twice and fail
     * on the primary key.
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> takeAdmission(TakeAdmissionRequest request, UserPrincipal user) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }
        String regid = text(request.regid());
        String itiCode = text(request.itiCode());
        String tradeCodeText = text(request.tradeCode());
        if (blank(regid)) {
            throw new IllegalArgumentException("regid is required");
        }
        if (blank(itiCode)) {
            throw new IllegalArgumentException("iti_code is required");
        }
        if (blank(tradeCodeText) || !tradeCodeText.matches(NUMERIC)) {
            throw new IllegalArgumentException("A numeric trade_code is required");
        }
        int tradeCode = Integer.parseInt(tradeCodeText);

        // An ITI login may only admit into its own ITI; a district login only into its own district.
        if (!blank(user.itiCode()) && !user.itiCode().equals(itiCode)) {
            throw new IllegalArgumentException("ITI " + user.itiCode()
                    + " cannot admit a candidate into ITI " + itiCode);
        }
        String distCode = districtOf(itiCode);
        if (distCode == null) {
            throw new IllegalArgumentException("Unknown ITI code: " + itiCode);
        }
        if (!blank(user.distCode()) && !user.distCode().equals(distCode)) {
            throw new IllegalArgumentException("District " + user.distCode()
                    + " cannot admit a candidate into district " + distCode);
        }

        Map<String, Object> config = currentPhaseConfig();
        String year = currentYear(config);
        int phase = currentPhase(config);
        int seatMatrixPhase = currentSeatMatrixPhase(config);

        // type_admission is NOT NULL on admissions.iti_admissions, and the page always has a board
        // selected by the time it confirms, so an empty value is a client error worth reporting.
        String typeAdmission = emptyToNull(request.typeAdmission());
        if (typeAdmission == null) {
            throw new IllegalArgumentException(
                    "type_admission (Type of Admission) is required to record an admission");
        }

        Map<String, Object> existing = findAdmission(regid);
        if (existing != null) {
            return failure("Candidate " + regid + " is already admitted (admission number "
                    + existing.get("adm_num") + ")");
        }

        List<Map<String, Object>> trades = jdbc.queryForList(
                "SELECT m.trade_short, COALESCE(m.trade_name, m.trade_short) AS trade_name "
                        + "FROM public.ititrade_master m WHERE m.trade_code = :tradeCode",
                new MapSqlParameterSource("tradeCode", tradeCode));
        if (trades.isEmpty()) {
            throw new IllegalArgumentException("Unknown trade code: " + tradeCode);
        }
        String tradeShort = text(trades.get(0).get("trade_short"));
        String tradeName = text(trades.get(0).get("trade_name"));
        if (blank(tradeShort)) {
            throw new IllegalArgumentException("Trade " + tradeCode
                    + " has no short code; cannot build an admission number");
        }

        Integer sanctioned = jdbc.getJdbcTemplate().queryForObject(
                "SELECT COUNT(*) FROM public.ititrade WHERE iti_code = ? AND trade_code = ?",
                Integer.class, itiCode, tradeCode);
        if (sanctioned == null || sanctioned == 0) {
            throw new IllegalArgumentException("Trade " + tradeName + " is not sanctioned for ITI " + itiCode);
        }

        String candidateTable = resolveCandidateTable(year);
        List<Map<String, Object>> candidateRows = jdbc.queryForList(
                "SELECT regid, name, caste, phc, exservice FROM public." + candidateTable
                        + " WHERE regid = CAST(:regid AS integer) LIMIT 1",
                new MapSqlParameterSource("regid", regid));
        if (candidateRows.isEmpty()) {
            throw new IllegalArgumentException("No application record for registration " + regid
                    + " in " + candidateTable);
        }
        Map<String, Object> candidate = candidateRows.get(0);

        String admNum = nextAdmissionNumber(itiCode, tradeShort, year);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("admNum", admNum)
                .addValue("regid", regid)
                .addValue("itiCode", itiCode)
                .addValue("distCode", distCode)
                .addValue("tradeCode", tradeCode)
                .addValue("year", year)
                .addValue("phase", phase)
                .addValue("typeAdmission", typeAdmission)
                .addValue("idmarks1", emptyToNull(request.idmarks1()))
                .addValue("idmarks2", emptyToNull(request.idmarks2()))
                .addValue("sscRegno", emptyToNull(request.sscRegno()))
                .addValue("sscBoard", emptyToNull(request.sscBoard()))
                .addValue("sscYear", emptyToNull(request.sscYear()))
                .addValue("sscMonth", emptyToNull(request.sscMonth()))
                .addValue("modeAdmission", DEFAULT_MODE_OF_ADMISSION);

        int inserted = jdbc.update(String.format(INSERT_ADMISSION_SQL, candidateTable), params);
        if (inserted != 1) {
            throw new IllegalStateException("Expected to admit exactly one candidate but wrote " + inserted);
        }

        boolean seatFilled = fillSeat(itiCode, tradeCode, year, seatMatrixPhase, candidate);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("adm_num", admNum);
        response.put("regid", regid);
        response.put("iti_code", itiCode);
        response.put("trade_code", tradeCode);
        response.put("trade_name", tradeName);
        response.put("year", year);
        response.put("phase", phase);
        response.put("date_of_admission", java.time.LocalDate.now().toString());
        if (!seatFilled) {
            response.put("seat_fill_warning", "Admission recorded, but no matching category bucket was "
                    + "found in iti_seatmatrix for year " + year + " phase " + seatMatrixPhase);
        }
        return response;
    }

    /** A refusal the page should render (already admitted, for instance), not a server error. */
    private static Map<String, Object> failure(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", message);
        return response;
    }

    private static String emptyToNull(String value) {
        return blank(value) ? null : value.trim();
    }

    /**
     * {@code <iti_code><trade_short><yy>} plus the next free serial, e.g. {@code 1536FI25012}.
     *
     * <p>The serial is the highest one already recorded for that prefix, so numbers continue the
     * published series instead of restarting.
     */
    private String nextAdmissionNumber(String itiCode, String tradeShort, String year) {
        String yy = year.length() >= 2 ? year.substring(year.length() - 2) : year;
        String prefix = itiCode + tradeShort + yy;
        int serialStart = prefix.length() + 1;
        Integer highest = jdbc.getJdbcTemplate().queryForObject(
                "SELECT COALESCE(MAX(SUBSTRING(adm_num FROM " + serialStart + ")::int), 0) "
                        + "FROM admissions.iti_admissions "
                        + "WHERE adm_num LIKE ? AND SUBSTRING(adm_num FROM " + serialStart + ") ~ '^[0-9]+$'",
                Integer.class, prefix + "%");
        int next = (highest == null ? 0 : highest) + 1;
        if (next > 999) {
            logger.warn("Admission serial for {} passed 999 ({}); admission numbers will grow a digit",
                    prefix, next);
        }
        return prefix + String.format("%03d", next);
    }

    /**
     * Increments the filled bucket and decrements the vacant one on the seat-matrix row the page is
     * showing.
     *
     * <p>The bucket is the candidate's reservation code: {@code PH} / {@code EX-S} from the flags,
     * otherwise the caste code, with {@code IM} for minority. A row whose hstore has no such key is
     * left untouched and reported through {@code seat_fill_warning} rather than guessed at.
     *
     * <p>{@code exist()} is used instead of hstore's {@code ?} operator because a bare {@code ?} is
     * read as a JDBC positional placeholder.
     */
    private boolean fillSeat(String itiCode, int tradeCode, String year, int seatMatrixPhase,
            Map<String, Object> candidate) {
        String category = categoryBucket(candidate);
        if (category == null) {
            logger.warn("Caste {} (phc={}, exservice={}) for regid {} maps to no seat-matrix bucket",
                    candidate.get("caste"), candidate.get("phc"), candidate.get("exservice"),
                    candidate.get("regid"));
            return false;
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("category", category)
                .addValue("itiCode", itiCode)
                .addValue("tradeCode", tradeCode)
                .addValue("year", year)
                .addValue("seatMatrixPhase", seatMatrixPhase);

        int updated = jdbc.update(
                "UPDATE public.iti_seatmatrix SET "
                        + "strength_fill = strength_fill || hstore(CAST(:category AS text), "
                        + "            ((COALESCE(strength_fill -> :category, '0')::int) + 1)::text), "
                        + "strength_vacant = strength_vacant || hstore(CAST(:category AS text), "
                        + "            ((COALESCE(strength_vacant -> :category, '0')::int) - 1)::text) "
                        + "WHERE iti_code = :itiCode AND trade_code = :tradeCode AND year = :year "
                        + "  AND phase = :seatMatrixPhase AND exist(strength_fill, CAST(:category AS text))",
                params);

        if (updated == 0) {
            logger.warn("No iti_seatmatrix row for iti {} trade {} year {} phase {} carries bucket {}",
                    itiCode, tradeCode, year, seatMatrixPhase, category);
            return false;
        }
        return true;
    }

    /** Buckets that exist in iti_seatmatrix.strength for the current year. */
    private static final Pattern SEAT_BUCKET =
            Pattern.compile("^(BC-[A-E]W?|SC-I{1,3}W?|ST|SP|EWS|OC|IM|PH|EX-S)$");

    /** Reservation bucket for a candidate, or null when the caste code cannot be mapped. */
    private static String categoryBucket(Map<String, Object> candidate) {
        if (isTrue(candidate.get("phc"))) {
            return "PH";
        }
        if (isTrue(candidate.get("exservice"))) {
            return "EX-S";
        }
        String caste = text(candidate.get("caste"));
        if (blank(caste)) {
            return null;
        }
        String code = caste.trim().toUpperCase(Locale.ROOT).replace(" ", "");
        if ("GEN".equals(code) || "GENERAL".equals(code)) {
            return "OC";
        }
        if ("IMC".equals(code) || "MINORITY".equals(code) || "MUSLIM".equals(code)) {
            return "IM";
        }
        return SEAT_BUCKET.matcher(code).matches() ? code : null;
    }

    private static boolean isTrue(Object value) {
        if (value instanceof Boolean flag) {
            return flag;
        }
        String text = text(value);
        return "t".equalsIgnoreCase(text) || "true".equalsIgnoreCase(text)
                || "y".equalsIgnoreCase(text) || "1".equals(text);
    }

    // ------------------------------------------------------------------ helpers

    private static String text(Object value) {
        return value == null ? null : String.valueOf(value).trim();
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }

    private static int number(Object value, int fallback) {
        if (value == null) {
            return fallback;
        }
        if (value instanceof Number n) {
            return n.intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(value).trim());
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private static Map<String, Object> tfv(Map<String, Object> row, String totalKey,
            String filledKey, String vacantKey) {
        Map<String, Object> cell = new LinkedHashMap<>();
        cell.put("T", number(row.get(totalKey), 0));
        cell.put("F", number(row.get(filledKey), 0));
        cell.put("V", number(row.get(vacantKey), 0));
        return cell;
    }

    /** An ITI login carries only the ITI code, so the district is read back from the ITI master. */
    private String districtOf(String itiCode) {
        if (blank(itiCode)) {
            return null;
        }
        List<String> districts = jdbc.queryForList(
                "SELECT dist_code FROM public.iti WHERE iti_code = :itiCode",
                new MapSqlParameterSource("itiCode", itiCode), String.class);
        return districts.isEmpty() ? null : districts.get(0);
    }
}
