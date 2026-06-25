package com.server.backend.service.Reports;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.Reports.AdmissionReportResponse;
import com.server.backend.DTO.Reports.AllResourceRoleResponse;
import com.server.backend.DTO.Reports.ApiDashboardResponse;
import com.server.backend.DTO.Reports.ApplicantCountResponse;
import com.server.backend.DTO.Reports.ApplicantMobileAddressResponse;
import com.server.backend.DTO.Reports.ApplicantReportResponse;
import com.server.backend.DTO.Reports.CasteWiseAdmissionsResponse;
import com.server.backend.DTO.Reports.CollegeWiseOpenSeatsResponse;
import com.server.backend.DTO.Reports.DistrictScheduleResponse;
import com.server.backend.DTO.Reports.DistrictWiseApplicationCountResponse;
import com.server.backend.DTO.Reports.DscFullReportResponse;
import com.server.backend.DTO.Reports.ITIAdmissionsReportResponse;
import com.server.backend.DTO.Reports.ItiWiseStatusResponse;
import com.server.backend.DTO.Reports.MetadataResponse;
import com.server.backend.DTO.Reports.OpenSeatsAbstractResponse;
import com.server.backend.DTO.Reports.PhaseWiseReportResponse;
import com.server.backend.DTO.Reports.ShiftUnitResponse;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.AdmissionDetail;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.AppliedIti;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.MeritListDetail;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.RegistrationDetail;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.SscMarksDetail;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.VerifiedDetail;
import com.server.backend.DTO.Reports.StudentListResponse;
import com.server.backend.DTO.Reports.TodayScheduleResponse;
import com.server.backend.DTO.Reports.TradeDurationSeatsResponse;
import com.server.backend.DTO.Reports.TradeWiseReportResponse;
import com.server.backend.DTO.Reports.VerifiedApplicationCountResponse;

@Service
public class ReportServiceImpl implements ReportService {

    private final JdbcTemplate jdbcTemplate;

    public ReportServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MetadataResponse getMetadata() {
        List<String> years = jdbcTemplate.queryForList(
                "SELECT DISTINCT value FROM public.iti_params WHERE code = '7' OR code = '8' ORDER BY value DESC",
                String.class);
        if (years.isEmpty()) {
            years = List.of("2024", "2025", "2026");
        }
        List<String> phases = List.of("1", "2", "3", "4", "5");
        List<String> castes = List.of("OC", "BC-A", "BC-B", "BC-C", "BC-D", "BC-E", "SC", "ST");
        List<String> levels = List.of("CONVENER", "ITI");
        return new MetadataResponse(years, phases, castes, levels);
    }

    // 1. Phase Wise Abstract
    @Override
    public List<PhaseWiseReportResponse> getPhaseWiseReport(String year) {
        String sql = """
            SELECT d.dist_name,
                   COUNT(a.*) FILTER (WHERE a.phase = 1) AS phase_i,
                   COUNT(a.*) FILTER (WHERE a.phase = 2) AS phase_ii,
                   COUNT(a.*) FILTER (WHERE a.phase = 3) AS phase_iii,
                   COUNT(a.*) FILTER (WHERE a.phase = 4) AS phase_iv,
                   COUNT(a.*) FILTER (WHERE a.phase = 5) AS phase_v,
                   COUNT(a.*) AS total,
                   COUNT(a.*) FILTER (WHERE a.date_of_admission = CURRENT_DATE) AS today
            FROM public.dist_mst d
            LEFT JOIN admissions.iti_admissions a
              ON d.dist_code = a.dist_code
              AND a.year_of_admission = ?
            GROUP BY d.dist_name
            ORDER BY d.dist_name
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new PhaseWiseReportResponse(
                rs.getString("dist_name"),
                rs.getInt("phase_i"),
                rs.getInt("phase_ii"),
                rs.getInt("phase_iii"),
                rs.getInt("phase_iv"),
                rs.getInt("phase_v"),
                rs.getInt("total"),
                rs.getInt("today")
        ), year);
    }

    // 2. Open Seats Abstract (District wise)
    @Override
    public List<OpenSeatsAbstractResponse> getOpenSeatsAbstract(String year) {
        String sql = """
            WITH individual_seats AS (
                SELECT i.dist_code, (svals(sm.strength))::int AS strength_val
                FROM public.iti_seatmatrix sm
                JOIN public.iti i ON sm.iti_code = i.iti_code
                WHERE sm.year::text = ?::text
            ),
            seat_capacity AS (
                SELECT dist_code, SUM(strength_val) AS total_seats
                FROM individual_seats GROUP BY dist_code
            ),
            filled_seats AS (
                SELECT dist_code, COUNT(*) AS total_filled
                FROM admissions.iti_admissions
                WHERE year_of_admission::text = ?::text
                GROUP BY dist_code
            )
            SELECT d.dist_code, d.dist_name,
                   COALESCE(sc.total_seats, 0) AS no_of_seats,
                   COALESCE(fs.total_filled, 0) AS fill,
                   (COALESCE(sc.total_seats, 0) - COALESCE(fs.total_filled, 0)) AS vacant
            FROM (SELECT DISTINCT dist_code, dist_name FROM public.dist_mst) d
            LEFT JOIN seat_capacity sc ON d.dist_code = sc.dist_code
            LEFT JOIN filled_seats fs ON d.dist_code = fs.dist_code
            ORDER BY d.dist_name
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new OpenSeatsAbstractResponse(
                rs.getString("dist_code"),
                rs.getString("dist_name"),
                rs.getInt("no_of_seats"),
                rs.getInt("fill"),
                rs.getInt("vacant")
        ), year, year);
    }

    // 3. College Wise Open Seats
    @Override
    public List<CollegeWiseOpenSeatsResponse> getCollegeWiseOpenSeats(String year, String distCode) {
        String sql = """
            WITH individual_seats AS (
                SELECT sm.iti_code, (svals(sm.strength))::int AS strength_val
                FROM public.iti_seatmatrix sm
                JOIN public.iti i ON sm.iti_code = i.iti_code
                WHERE sm.year::text = ?::text AND i.dist_code = ?
            ),
            college_capacity AS (
                SELECT iti_code, SUM(strength_val) AS total_seats
                FROM individual_seats GROUP BY iti_code
            ),
            filled_seats AS (
                SELECT iti_code, COUNT(*) AS total_filled
                FROM admissions.iti_admissions
                WHERE year_of_admission::text = ?::text AND dist_code = ?
                GROUP BY iti_code
            )
            SELECT i.iti_code, i.iti_name,
                   COALESCE(cc.total_seats, 0) AS no_of_seats,
                   COALESCE(fs.total_filled, 0) AS fill,
                   (COALESCE(cc.total_seats, 0) - COALESCE(fs.total_filled, 0)) AS vacant
            FROM public.iti i
            LEFT JOIN college_capacity cc ON i.iti_code = cc.iti_code
            LEFT JOIN filled_seats fs ON i.iti_code = fs.iti_code
            WHERE i.dist_code = ? AND (cc.total_seats > 0 OR fs.total_filled > 0)
            ORDER BY i.iti_name
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CollegeWiseOpenSeatsResponse(
                rs.getString("iti_code"),
                rs.getString("iti_name"),
                rs.getInt("no_of_seats"),
                rs.getInt("fill"),
                rs.getInt("vacant")
        ), year, distCode, year, distCode, distCode);
    }

    // 4. Trade Duration Seats Abstract
    @Override
    public List<TradeDurationSeatsResponse> getTradeDurationSeats(String year, String durationMonths, String itiType) {
        String durationyrs = String.valueOf(Integer.parseInt(durationMonths) / 12);
        String sql = """
            WITH unique_iti AS (
                SELECT DISTINCT ON (iti_code) iti_code, govt FROM public.iti
            ),
            unique_trades AS (
                SELECT DISTINCT ON (trade_code) trade_code, trade_name, durationyrs FROM public.ititrade_master
            ),
            individual_seats AS (
                SELECT sm.trade_code, ut.trade_name, (svals(sm.strength))::int AS strength_val
                FROM public.iti_seatmatrix sm
                JOIN unique_iti ui ON sm.iti_code = ui.iti_code
                JOIN unique_trades ut ON sm.trade_code::text = ut.trade_code::text
                WHERE sm.year::text = ?::text
                  AND ut.durationyrs::text LIKE ? || '%'
                  AND ui.govt = ?
            ),
            trade_seats AS (
                SELECT trade_code, trade_name, SUM(strength_val) AS strength
                FROM individual_seats GROUP BY trade_code, trade_name
            ),
            trade_filled AS (
                SELECT a.trade_code, COUNT(DISTINCT a.adm_num) AS fill
                FROM admissions.iti_admissions a
                JOIN unique_iti ui ON a.iti_code = ui.iti_code
                JOIN unique_trades ut ON a.trade_code::text = ut.trade_code::text
                WHERE a.year_of_admission::text = ?::text
                  AND ut.durationyrs::text LIKE ? || '%'
                  AND ui.govt = ?
                GROUP BY a.trade_code
            )
            SELECT ts.trade_code, ts.trade_name, ts.strength,
                   COALESCE(tf.fill, 0) AS fill,
                   (ts.strength - COALESCE(tf.fill, 0)) AS vacant,
                   CASE WHEN ts.strength > 0
                        THEN ROUND((COALESCE(tf.fill, 0)::numeric / ts.strength::numeric) * 100, 2)
                        ELSE 0 END AS fill_percentage
            FROM trade_seats ts
            LEFT JOIN trade_filled tf ON ts.trade_code::text = tf.trade_code::text
            ORDER BY ts.trade_name
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TradeDurationSeatsResponse(
                rs.getString("trade_code"),
                rs.getString("trade_name"),
                rs.getInt("strength"),
                rs.getInt("fill"),
                rs.getInt("vacant"),
                rs.getDouble("fill_percentage")
        ), year, durationyrs, itiType, year, durationyrs, itiType);
    }

    // 5. Admission Report (Trade wise - boys/girls)
    @Override
    public List<AdmissionReportResponse> getAdmissionReport(String year, String caste, String pwd) {
        StringBuilder sql = new StringBuilder("""
            SELECT tm.trade_name,
                   COUNT(*) FILTER (WHERE UPPER(a.gender) LIKE 'M%') AS boys,
                   COUNT(*) FILTER (WHERE UPPER(a.gender) LIKE 'F%') AS girls,
                   COUNT(*) AS total
            FROM admissions.iti_admissions a
            JOIN public.ititrade_master tm ON a.trade_code = tm.trade_code
            WHERE a.year_of_admission::text = ?::text
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);

        if (caste != null && !"All".equalsIgnoreCase(caste)) {
            sql.append(" AND a.res_category = ?");
            params.add(caste);
        }
        if ("Yes".equalsIgnoreCase(pwd)) {
            sql.append(" AND a.phc = 'Y'");
        } else if ("No".equalsIgnoreCase(pwd)) {
            sql.append(" AND a.phc = 'N'");
        }

        sql.append(" GROUP BY tm.trade_name ORDER BY tm.trade_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new AdmissionReportResponse(
                rs.getString("trade_name"),
                rs.getInt("boys"),
                rs.getInt("girls"),
                rs.getInt("total")
        ), params.toArray());
    }

    // 6. Applicant Count Nodal
    @Override
    public ApplicantCountResponse getApplicantCount(String year, String phase) {
        String sql = """
            SELECT d.dist_code, d.dist_name,
                   COUNT(sa.regid) FILTER (WHERE i.govt = 'G') AS govt_count,
                   COUNT(sa.regid) FILTER (WHERE i.govt != 'G') AS pvt_count,
                   COUNT(sa.regid) AS total_count
            FROM public.dist_mst d
            LEFT JOIN public.iti i ON d.dist_code = i.dist_code
            LEFT JOIN public.application sa ON i.iti_code = sa.user_id
                AND sa.year::text = ?::text
                AND sa.phase::text ILIKE '%\"' || ? || '\"=>\"true\"%'
            GROUP BY d.dist_code, d.dist_name
            ORDER BY d.dist_name
            """;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, year, phase);

        List<ApplicantCountResponse.DistCountRow> govtPvt = new ArrayList<>();
        List<ApplicantCountResponse.DistCountRow> govt = new ArrayList<>();
        List<ApplicantCountResponse.DistCountRow> pvt = new ArrayList<>();
        int govtPvtTotal = 0, govtTotal = 0, pvtTotal = 0;

        for (Map<String, Object> row : rows) {
            String distName = str(row.get("dist_name"));
            int totalVal = ((Number) row.get("total_count")).intValue();
            int govtVal = ((Number) row.get("govt_count")).intValue();
            int pvtVal = ((Number) row.get("pvt_count")).intValue();

            govtPvt.add(new ApplicantCountResponse.DistCountRow(distName, totalVal));
            govt.add(new ApplicantCountResponse.DistCountRow(distName, govtVal));
            pvt.add(new ApplicantCountResponse.DistCountRow(distName, pvtVal));
            govtPvtTotal += totalVal;
            govtTotal += govtVal;
            pvtTotal += pvtVal;
        }

        return new ApplicantCountResponse(govtPvt, govt, pvt, govtPvtTotal, govtTotal, pvtTotal);
    }

    // 7. ITI Admissions Report
    @Override
    public List<ITIAdmissionsReportResponse> getITIAdmissionsReport(String year, String distCode, String govt,
            String caste, String gender, String ncvtScvt, String limitRows) {
        StringBuilder sql = new StringBuilder("""
            SELECT DISTINCT a.adm_num AS admission_number, a.name, a.ssc_regno, a.year_of_admission
            FROM admissions.iti_admissions a
            LEFT JOIN public.iti i ON TRIM(a.iti_code::text) = TRIM(i.iti_code::text)
            WHERE a.year_of_admission::text = ?::text
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);
        int paramIdx = 2;

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND TRIM(a.dist_code::text) = TRIM(?" + paramIdx++ + "::text)");
            params.add(distCode);
        }
        if (govt != null && !"All".equalsIgnoreCase(govt)) {
            sql.append(" AND i.govt = ?" + paramIdx++);
            params.add("Govt".equalsIgnoreCase(govt) ? "G" : "P");
        }
        if (caste != null && !"All".equalsIgnoreCase(caste)) {
            sql.append(" AND TRIM(a.res_category) = ?" + paramIdx++);
            params.add(caste);
        }
        if (gender != null && !"All".equalsIgnoreCase(gender)) {
            String genderVal = gender.toUpperCase().startsWith("M") ? "M%" : "F%";
            sql.append(" AND a.gender ILIKE ?" + paramIdx++);
            params.add(genderVal);
        }
        if (ncvtScvt != null && !"All".equalsIgnoreCase(ncvtScvt)) {
            String typeVal = ncvtScvt.toUpperCase().startsWith("N") ? "N" : "S";
            sql.append(" AND TRIM(a.type_admission) = ?" + paramIdx++);
            params.add(typeVal);
        }

        sql.append(" ORDER BY a.name");

        if (limitRows != null && !"All".equalsIgnoreCase(limitRows) && !limitRows.isEmpty()) {
            sql.append(" LIMIT ?" + paramIdx++);
            params.add(Integer.parseInt(limitRows));
        } else {
            sql.append(" LIMIT 500");
        }

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ITIAdmissionsReportResponse(
                rs.getString("admission_number"),
                rs.getString("name"),
                rs.getString("ssc_regno"),
                rs.getString("year_of_admission")
        ), params.toArray());
    }

    // 8. DSC Full Report
    @Override
    public DscFullReportResponse getDscFullReport(String distCode, String itiCode, String tradeCode,
            String phase, String year, String modeAdm) {
        DscFullReportResponse response = new DscFullReportResponse();

        // Get ITI & Trade info
        try {
            String itiSql = "SELECT iti_name FROM public.iti WHERE iti_code = ?";
            response.setItiName(jdbcTemplate.queryForObject(itiSql, String.class, itiCode));
        } catch (Exception e) {
            response.setItiName(itiCode);
        }
        try {
            String tradeSql = "SELECT trade_name FROM public.ititrade_master WHERE trade_code = ?";
            response.setTradeName(jdbcTemplate.queryForObject(tradeSql, String.class, Integer.parseInt(tradeCode)));
        } catch (Exception e) {
            response.setTradeName(tradeCode);
        }
        response.setSession("AUGUST-" + year);

        // Get seat matrix categories
        String seatSql = """
            SELECT DISTINCT ON (a.category_code)
                   a.category_code,
                   a.strength::int,
                   a.strength_fill::int,
                   a.strength_vacant::int,
                   b.category_order
            FROM (
                SELECT skeys(strength) AS category_code,
                       svals(strength) AS strength,
                       svals(strength_fill) AS strength_fill,
                       svals(strength_vacant) AS strength_vacant
                FROM public.iti_seatmatrix
                WHERE iti_code = ? AND trade_code::text = ? AND year = ?
            ) a
            LEFT JOIN public.category_mast b ON a.category_code = b.category_code
            ORDER BY a.category_code
            """;
        List<Map<String, Object>> catRows = jdbcTemplate.queryForList(seatSql, itiCode, tradeCode, year);

        // Get candidates
        String candSql = """
            SELECT DISTINCT ON (a.adm_num)
                   NULLIF(r.rank,'')::int AS rank,
                   a.adm_num, a.name, a.fname, a.gender,
                   TO_CHAR(a.dob, 'DD-MM-YYYY') AS dob, a.caste, a.res_category
            FROM admissions.iti_admissions a
            LEFT JOIN ranks r ON a.regid::int = r.regid
            WHERE TRIM(a.iti_code::text) = TRIM(?::text)
              AND TRIM(a.trade_code::text) = TRIM(?::text)
              AND a.phase = ?
              AND a.year_of_admission = ?
              AND UPPER(TRIM(a.mode_adm::text)) = UPPER(TRIM(?::text))
            ORDER BY a.adm_num, NULLIF(r.rank,'')::int NULLS LAST
            """;
        List<Map<String, Object>> candRows = jdbcTemplate.queryForList(candSql,
                itiCode, tradeCode, Integer.parseInt(phase), year, modeAdm.toUpperCase());

        // Group candidates by category
        Map<String, List<DscFullReportResponse.CandidateRow>> candidatesByCategory = new LinkedHashMap<>();
        int slNo = 1;
        for (Map<String, Object> cand : candRows) {
            String category = normalizeCategory(str(cand.get("res_category")));
            candidatesByCategory.computeIfAbsent(category, k -> new ArrayList<>())
                .add(new DscFullReportResponse.CandidateRow(
                        slNo++,
                        str(cand.get("rank")),
                        str(cand.get("adm_num")),
                        str(cand.get("name")),
                        str(cand.get("fname")),
                        str(cand.get("gender")),
                        str(cand.get("dob")),
                        str(cand.get("caste"))
                ));
        }

        // Build category groups
        List<DscFullReportResponse.CategoryGroup> categories = new ArrayList<>();
        for (Map<String, Object> cat : catRows) {
            String catCode = str(cat.get("category_code"));
            int strength = ((Number) cat.get("strength")).intValue();
            int filled = ((Number) cat.get("strength_fill")).intValue();
            String normCat = normalizeCategory(catCode);
            List<DscFullReportResponse.CandidateRow> candList = candidatesByCategory.getOrDefault(normCat, new ArrayList<>());
            int vacant = Math.max(0, strength - candList.size());

            // Add vacant rows
            for (int i = 0; i < vacant; i++) {
                candList.add(new DscFullReportResponse.CandidateRow(
                        candList.size() + 1, null, "VACANT", "VACANT", "", "", "", ""));
            }

            categories.add(new DscFullReportResponse.CategoryGroup(catCode, strength, filled, vacant, candList));
        }

        response.setCategories(categories);
        return response;
    }

    private String normalizeCategory(String c) {
        return c != null ? c.replaceAll("[\\s\\-_]", "").toUpperCase() : "UNKNOWN";
    }

    // 9. API Dashboard
    @Override
    public List<ApiDashboardResponse> getApiDashboard(String year) {
        String sql = """
            WITH phone_duplicates AS (
              SELECT phno, dist_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE year_of_admission = ? AND phno IS NOT NULL
              GROUP BY phno, dist_code HAVING COUNT(*) > 1
            ),
            aadhar_duplicates AS (
              SELECT adarno, dist_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE year_of_admission = ? AND adarno IS NOT NULL AND adarno != ''
              GROUP BY adarno, dist_code HAVING COUNT(*) > 1
            ),
            email_duplicates AS (
              SELECT email_id, dist_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE year_of_admission = ? AND email_id IS NOT NULL AND email_id != ''
              GROUP BY email_id, dist_code HAVING COUNT(*) > 1
            )
            SELECT d.dist_code, d.dist_name,
                   COUNT(a.*) AS total,
                   COUNT(*) FILTER (WHERE a.rec_status = 'S') AS success,
                   COUNT(*) FILTER (WHERE a.rec_status = 'N') AS pending_sid,
                   COUNT(*) FILTER (WHERE a.rec_status = 'D') AS verified,
                   COUNT(*) FILTER (WHERE a.rec_status = 'E') AS to_be_verified,
                   COUNT(*) FILTER (WHERE a.rec_status IS NULL OR a.rec_status = '') AS to_be_updated,
                   COUNT(DISTINCT pd.phno) FILTER (WHERE pd.phno IS NOT NULL) AS phone_duplicate_records,
                   COUNT(DISTINCT ad.adarno) FILTER (WHERE ad.adarno IS NOT NULL) AS aadhar_duplicate_records,
                   COUNT(DISTINCT ed.email_id) FILTER (WHERE ed.email_id IS NOT NULL) AS email_duplicate_records
            FROM public.dist_mst d
            LEFT JOIN admissions.iti_admissions a
              ON d.dist_code = a.dist_code AND a.year_of_admission = ?
            LEFT JOIN phone_duplicates pd ON a.phno = pd.phno AND a.dist_code = pd.dist_code
            LEFT JOIN aadhar_duplicates ad ON a.adarno = ad.adarno AND a.dist_code = ad.dist_code
            LEFT JOIN email_duplicates ed ON a.email_id = ed.email_id AND a.dist_code = ed.dist_code
            GROUP BY d.dist_code, d.dist_name
            ORDER BY d.dist_name
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ApiDashboardResponse(
                rs.getString("dist_name"),
                rs.getInt("total"),
                rs.getInt("success"),
                rs.getInt("verified"),
                rs.getInt("to_be_verified"),
                rs.getInt("to_be_updated"),
                rs.getInt("phone_duplicate_records"),
                rs.getInt("aadhar_duplicate_records"),
                rs.getInt("email_duplicate_records")
        ), year, year, year, year);
    }

    // 10. Student Complete Details
    @Override
    public StudentCompleteDetailsResponse getStudentCompleteDetails(String regid, String admNum) {
        StudentCompleteDetailsResponse response = new StudentCompleteDetailsResponse();

        if (admNum != null && !admNum.isEmpty()) {
            try {
                String findSql = "SELECT regid FROM admissions.iti_admissions WHERE adm_num = ? LIMIT 1";
                regid = jdbcTemplate.queryForObject(findSql, String.class, admNum);
            } catch (Exception e) {
                return response;
            }
        }

        if (regid == null || regid.isEmpty()) return response;
        String finalRegid = regid;

        // Detect year from regid prefix
        int detectedYear = 2024;
        try {
            String prefix = regid.substring(0, 2);
            detectedYear = Integer.parseInt("20" + prefix);
        } catch (Exception e) {
            detectedYear = 2024;
        }

        // 1. Registration details
        String regSql = """
            SELECT regid::text AS registration_id, name, fname AS father_name, addr AS address,
                   mname AS mother_name, phno::text AS phone_no, gender, caste, ssc_passed, phc,
                   dob::text AS date_of_birth, ssc_regno AS ssc_ht_no, ssc_year AS ssc_pass_year,
                   phase::text AS registered_phase, entry_date::text AS registration_date,
                   verified_date::text AS verified_date
            FROM public.application WHERE regid::text = ?
            """;
        try {
            List<Map<String, Object>> regRows = jdbcTemplate.queryForList(regSql, finalRegid);
            if (!regRows.isEmpty()) {
                Map<String, Object> r = regRows.get(0);
                response.setRegistration(new RegistrationDetail(
                        str(r.get("name")), str(r.get("registration_id")), str(r.get("date_of_birth")),
                        str(r.get("ssc_ht_no")), str(r.get("father_name")), str(r.get("mother_name")),
                        str(r.get("address")), str(r.get("phone_no")), str(r.get("gender")),
                        str(r.get("caste")), str(r.get("ssc_passed")), str(r.get("phc")),
                        str(r.get("ssc_pass_year")), str(r.get("registered_phase")),
                        str(r.get("registration_date")), str(r.get("verified_date"))
                ));
            } else {
                // Fallback to iti_admissions
                String fallbackSql = """
                    SELECT regid::text AS registration_id, name, fname AS father_name, addr AS address,
                           mname AS mother_name, phno::text AS phone_no, gender, caste, ssc_passed, phc,
                           dob::text AS date_of_birth, ssc_regno AS ssc_ht_no, ssc_year AS ssc_pass_year,
                           phase::text AS registered_phase, date_of_admission::text AS registration_date,
                           NULL AS verified_date
                    FROM admissions.iti_admissions WHERE regid::text = ?::text LIMIT 1
                    """;
                List<Map<String, Object>> fallbackRows = jdbcTemplate.queryForList(fallbackSql, finalRegid);
                if (!fallbackRows.isEmpty()) {
                    Map<String, Object> r = fallbackRows.get(0);
                    response.setRegistration(new RegistrationDetail(
                            str(r.get("name")), str(r.get("registration_id")), str(r.get("date_of_birth")),
                            str(r.get("ssc_ht_no")), str(r.get("father_name")), str(r.get("mother_name")),
                            str(r.get("address")), str(r.get("phone_no")), str(r.get("gender")),
                            str(r.get("caste")), str(r.get("ssc_passed")), str(r.get("phc")),
                            str(r.get("ssc_pass_year")), str(r.get("registered_phase")),
                            str(r.get("registration_date")), str(r.get("verified_date"))
                    ));
                }
            }
        } catch (Exception e) {
            // Not found
        }

        // 2. SSC Marks
        String marksTable = (detectedYear == 2023) ? "public.cand_marks2023" : "public.cand_marks";
        String marksSql = """
            SELECT ssc_first_lang_marks AS first_language, ssc_second_lang_marks AS second_language,
                   ssc_eng_marks AS english, ssc_math_marks AS maths, ssc_sci_marks AS science,
                   ssc_social_marks AS social, ssc_tot_marks AS total
            FROM """ + marksTable + " WHERE regid::text = ?::text";
        try {
            List<Map<String, Object>> marksRows = jdbcTemplate.queryForList(marksSql, finalRegid);
            if (!marksRows.isEmpty()) {
                Map<String, Object> m = marksRows.get(0);
                response.setSscMarks(new SscMarksDetail(
                        val(m.get("first_language")), val(m.get("second_language")),
                        val(m.get("english")), val(m.get("maths")),
                        val(m.get("science")), val(m.get("social")),
                        val(m.get("total"))
                ));
            }
        } catch (Exception e) {
            // Not found
        }

        // 3. Applied ITIs
        String appliedSql = """
            SELECT c.iti_code, i.iti_name, c.phase, sa.year AS admissions_year
            FROM public.checklist c
            LEFT JOIN public.iti i ON c.iti_code = i.iti_code
            LEFT JOIN public.application sa ON c.regid = sa.regid
            WHERE c.regid = ?
            ORDER BY c.phase, i.iti_name
            """;
        try {
            List<Map<String, Object>> appliedRows = jdbcTemplate.queryForList(appliedSql, Integer.parseInt(finalRegid));
            List<AppliedIti> appliedList = new ArrayList<>();
            for (Map<String, Object> ap : appliedRows) {
                appliedList.add(new AppliedIti(
                        str(ap.get("iti_code")), str(ap.get("iti_name")),
                        str(ap.get("phase")), str(ap.get("admissions_year"))
                ));
            }
            response.setAppliedItis(appliedList);
        } catch (Exception e) {
            response.setAppliedItis(new ArrayList<>());
        }

        // 4. Verified details (same as registration)
        if (response.getRegistration() != null) {
            RegistrationDetail reg = response.getRegistration();
            response.setVerified(new VerifiedDetail(
                    reg.getName(), reg.getRegistrationId(), reg.getDateOfBirth(),
                    reg.getSscHtNo(), reg.getFatherName(), reg.getMotherName(),
                    reg.getAddress(), reg.getPhoneNo(), reg.getGender(),
                    reg.getCaste(), reg.getSscPassed(), reg.getPhc(),
                    reg.getSscPassYear(), reg.getRegisteredPhase(), reg.getRegistrationDate()
            ));
        }

        // 5. Merit list
        String meritSql = """
            SELECT d.dist_name, i.iti_name, r.rank, r.phase, r.qual AS qualification
            FROM public.ranks r
            LEFT JOIN public.dist_mst d ON r.dist_code = d.dist_code
            LEFT JOIN public.iti i ON r.iti_code = i.iti_code
            WHERE r.regid = ?
            ORDER BY r.phase
            """;
        try {
            List<Map<String, Object>> meritRows = jdbcTemplate.queryForList(meritSql, Integer.parseInt(finalRegid));
            List<MeritListDetail> meritList = new ArrayList<>();
            for (Map<String, Object> m : meritRows) {
                meritList.add(new MeritListDetail(
                        str(m.get("dist_name")), str(m.get("iti_name")),
                        str(m.get("rank")), str(m.get("phase")), str(m.get("qualification"))
                ));
            }
            response.setMeritList(meritList);
        } catch (Exception e) {
            response.setMeritList(new ArrayList<>());
        }

        // 6. Admission details
        String admSql = """
            SELECT d.dist_name AS district, i.iti_name AS iti, tm.trade_name AS trade,
                   a.adm_num AS admission_number, a.res_category AS reservation_category,
                   a.year_of_admission, a.phase::text, a.date_of_admission::text, a.phno::text AS phone_number
            FROM admissions.iti_admissions a
            LEFT JOIN public.dist_mst d ON a.dist_code = d.dist_code
            LEFT JOIN public.iti i ON a.iti_code = i.iti_code
            LEFT JOIN public.ititrade_master tm ON a.trade_code = tm.trade_code
            WHERE a.regid::text = ?::text
            """;
        try {
            List<Map<String, Object>> admRows = jdbcTemplate.queryForList(admSql, finalRegid);
            if (!admRows.isEmpty()) {
                Map<String, Object> ad = admRows.get(0);
                response.setAdmission(new AdmissionDetail(
                        str(ad.get("district")), str(ad.get("iti")), str(ad.get("trade")),
                        str(ad.get("admission_number")), str(ad.get("reservation_category")),
                        str(ad.get("year_of_admission")), str(ad.get("phase")),
                        str(ad.get("date_of_admission")), str(ad.get("phone_number"))
                ));
            }
        } catch (Exception e) {
            // Not found
        }

        return response;
    }

    // 11. Caste Wise Admissions Abstract
    @Override
    public List<CasteWiseAdmissionsResponse> getCasteWiseAdmissions(String year, String distCode, String govt, String phase, String gender) {
        StringBuilder sql = new StringBuilder("""
            SELECT d.dist_code AS "District Code", d.dist_name AS "District Name",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'BC-A%') AS "BC-A",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'BC-B%') AS "BC-B",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'BC-C%') AS "BC-C",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'BC-D%') AS "BC-D",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'BC-E%') AS "BC-E",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'EWS%') AS "EWS",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'EX-S%') AS "EX-S",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'IM%') AS "IM",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'OC%') AS "OC",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'PH%') AS "PH",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'SC-I%') AS "SC-I",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'SC-II%') AS "SC-II",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'SC-III%') AS "SC-III",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'SP%') AS "SP",
                   COUNT(*) FILTER (WHERE a.res_category ILIKE 'ST%') AS "ST"
            FROM public.dist_mst d
            LEFT JOIN admissions.iti_admissions a ON d.dist_code = a.dist_code AND a.year_of_admission::text = ?::text
            LEFT JOIN public.iti i ON a.iti_code = i.iti_code
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND TRIM(d.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }
        if (govt != null && !"All".equalsIgnoreCase(govt)) {
            sql.append(" AND i.govt = ?");
            params.add(govt);
        }
        if (phase != null && !"All".equalsIgnoreCase(phase) && !"0".equals(phase)) {
            sql.append(" AND a.phase = ?");
            params.add(Integer.parseInt(phase));
        }
        if (gender != null && !"All".equalsIgnoreCase(gender)) {
            sql.append(" AND a.gender = ?");
            params.add(gender);
        }

        sql.append(" GROUP BY d.dist_code, d.dist_name ORDER BY d.dist_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new CasteWiseAdmissionsResponse(
                rs.getString("District Code"),
                rs.getString("District Name"),
                rs.getInt("BC-A"), rs.getInt("BC-B"), rs.getInt("BC-C"),
                rs.getInt("BC-D"), rs.getInt("BC-E"), rs.getInt("EWS"),
                rs.getInt("EX-S"), rs.getInt("IM"), rs.getInt("OC"),
                rs.getInt("PH"), rs.getInt("SC-I"), rs.getInt("SC-II"),
                rs.getInt("SC-III"), rs.getInt("SP"), rs.getInt("ST")
        ), params.toArray());
    }

    // 12. Verified Application Count
    @Override
    public List<VerifiedApplicationCountResponse> getVerifiedApplicationCount(String year, String distCode) {
        StringBuilder sql = new StringBuilder("""
            SELECT d.dist_name AS "District Name",
                   COUNT(a.regid) AS "Total Applications",
                   COUNT(a.regid) FILTER (WHERE a.app_status = 'A') AS "Approved",
                   COUNT(a.regid) FILTER (WHERE a.app_status = 'R') AS "Rejected",
                   COUNT(a.regid) FILTER (WHERE a.app_status IS NULL OR a.app_status NOT IN ('A', 'R')) AS "Unverified"
            FROM (SELECT DISTINCT dist_code, dist_name FROM public.dist_mst) d
            LEFT JOIN public.iti i ON d.dist_code = i.dist_code
            LEFT JOIN public.application a ON i.iti_code = a.user_id AND a.year::text = ?::text
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND TRIM(d.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }

        sql.append(" GROUP BY d.dist_name ORDER BY d.dist_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new VerifiedApplicationCountResponse(
                rs.getString("District Name"),
                rs.getInt("Total Applications"),
                rs.getInt("Approved"),
                rs.getInt("Rejected"),
                rs.getInt("Unverified")
        ), params.toArray());
    }

    // 13. Permitted Shift Unit Report
    @Override
    public List<ShiftUnitResponse> getPermittedShiftUnit(String distCode, String itiCode) {
        StringBuilder sql = new StringBuilder("""
            SELECT i.iti_name, i.govt AS iti_type, tm.trade_name, sup.strength,
                   sup.shift_allowed AS shift, sup.unit_allowed AS unit
            FROM public.shift_unit_permitted sup
            JOIN public.iti i ON sup.iti_code::text = i.iti_code::text
            JOIN public.ititrade_master tm ON sup.trade_code::text = tm.trade_code::text
            WHERE TRIM(i.dist_code::text) = TRIM(?::text)
            """);
        List<Object> params = new ArrayList<>();
        params.add(distCode);

        if (itiCode != null && !"All".equalsIgnoreCase(itiCode) && !itiCode.isEmpty()) {
            sql.append(" AND TRIM(i.iti_code::text) = TRIM(?::text)");
            params.add(itiCode);
        }

        sql.append(" ORDER BY i.iti_name, tm.trade_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ShiftUnitResponse(
                rs.getString("iti_name"),
                rs.getString("iti_type"),
                rs.getString("trade_name"),
                rs.getInt("strength"),
                rs.getString("shift"),
                rs.getString("unit")
        ), params.toArray());
    }

    // 14. Applicant Report by Phase
    @Override
    public List<ApplicantReportResponse> getApplicantReportByPhase(String phase, String year, String itiCode, String distCode) {
        StringBuilder sql = new StringBuilder("""
            SELECT sa.ssc_regno, sa.phno AS mobile_no, sa.regid AS reg_id,
                   sa.name, sa.fname AS father_name, sa.mname AS mother_name
            FROM public.application sa
            LEFT JOIN public.iti i ON sa.user_id = i.iti_code
            WHERE sa.phase::text ILIKE '%\"' || ? || '\"=>\"true\"%'
            """);
        List<Object> params = new ArrayList<>();
        params.add(phase);

        if (year != null && !year.isEmpty()) {
            sql.append(" AND sa.year::text = ?::text");
            params.add(year);
        }
        if (itiCode != null && !"All".equalsIgnoreCase(itiCode) && !itiCode.isEmpty()) {
            sql.append(" AND i.iti_code = ?");
            params.add(itiCode);
        }
        if (distCode != null && !"All".equalsIgnoreCase(distCode) && !distCode.isEmpty()) {
            sql.append(" AND i.dist_code = ?");
            params.add(distCode);
        }

        sql.append(" ORDER BY sa.name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ApplicantReportResponse(
                rs.getString("ssc_regno"),
                rs.getString("mobile_no"),
                rs.getString("reg_id"),
                rs.getString("name"),
                rs.getString("father_name"),
                rs.getString("mother_name")
        ), params.toArray());
    }

    // 15. ITI Wise Status Report
    @Override
    public List<ItiWiseStatusResponse> getItiWiseStatus(String year, String distCode, String itiCode) {
        StringBuilder sql = new StringBuilder("""
            WITH phone_duplicates AS (
              SELECT phno, iti_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE year_of_admission::text = ?::text AND phno IS NOT NULL
              GROUP BY phno, iti_code HAVING COUNT(*) > 1
            ),
            aadhar_duplicates AS (
              SELECT adarno, iti_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE year_of_admission::text = ?::text AND adarno IS NOT NULL AND adarno != ''
              GROUP BY adarno, iti_code HAVING COUNT(*) > 1
            ),
            email_duplicates AS (
              SELECT email_id, iti_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE year_of_admission::text = ?::text AND email_id IS NOT NULL AND email_id != ''
              GROUP BY email_id, iti_code HAVING COUNT(*) > 1
            )
            SELECT d.dist_name, i.iti_name, i.iti_code,
                   COUNT(a.adm_num) AS total,
                   COUNT(a.adm_num) FILTER (WHERE a.rec_status = 'S') AS success,
                   COUNT(a.adm_num) FILTER (WHERE a.rec_status = 'N') AS pending_sid,
                   COUNT(a.adm_num) FILTER (WHERE a.rec_status = 'D') AS verified,
                   COUNT(a.adm_num) FILTER (WHERE a.rec_status = 'E') AS to_be_verified,
                   COUNT(a.adm_num) FILTER (WHERE a.rec_status IS NULL OR a.rec_status = '') AS to_be_updated,
                   COUNT(DISTINCT pd.phno) FILTER (WHERE pd.phno IS NOT NULL) AS phone_duplicate_records,
                   COUNT(DISTINCT ad.adarno) FILTER (WHERE ad.adarno IS NOT NULL) AS aadhar_duplicate_records,
                   COUNT(DISTINCT ed.email_id) FILTER (WHERE ed.email_id IS NOT NULL) AS email_duplicate_records
            FROM (SELECT DISTINCT dist_code, dist_name FROM public.dist_mst) d
            JOIN public.iti i ON d.dist_code = i.dist_code
            LEFT JOIN admissions.iti_admissions a ON i.iti_code = a.iti_code AND a.year_of_admission::text = ?::text
            LEFT JOIN phone_duplicates pd ON a.phno = pd.phno AND a.iti_code = pd.iti_code
            LEFT JOIN aadhar_duplicates ad ON a.adarno = ad.adarno AND a.iti_code = ad.iti_code
            LEFT JOIN email_duplicates ed ON a.email_id = ed.email_id AND a.iti_code = ed.iti_code
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        params.add(year); params.add(year); params.add(year); params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND TRIM(d.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }
        if (itiCode != null && !"All".equalsIgnoreCase(itiCode) && !itiCode.isEmpty()) {
            sql.append(" AND TRIM(i.iti_code::text) = TRIM(?::text)");
            params.add(itiCode);
        }

        sql.append(" GROUP BY d.dist_name, i.iti_name, i.iti_code ORDER BY d.dist_name, i.iti_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ItiWiseStatusResponse(
                rs.getString("dist_name"),
                rs.getString("iti_name"),
                rs.getString("iti_code"),
                rs.getInt("total"),
                rs.getInt("success"),
                rs.getInt("pending_sid"),
                rs.getInt("verified"),
                rs.getInt("to_be_verified"),
                rs.getInt("to_be_updated"),
                rs.getInt("phone_duplicate_records"),
                rs.getInt("aadhar_duplicate_records"),
                rs.getInt("email_duplicate_records")
        ), params.toArray());
    }

    // 16. ITI Student List
    @Override
    public List<StudentListResponse> getItiStudentList(String year, String itiCode, String distCode) {
        StringBuilder sql = new StringBuilder("""
            SELECT a.adm_num AS admission_no, a.trade_code AS trade_code, a.ssc_regno AS ssc_hall_ticket,
                   a.name, a.fname AS father_name, a.mname AS mother_name,
                   TO_CHAR(a.dob, 'DD-MM-YYYY') AS date_of_birth,
                   a.phno AS mobile_no, a.email_id AS email,
                   a.shift, a.unit, a.pwd_category AS pwd_category,
                   CASE WHEN a.economic_weaker_section = true THEN 'YES' ELSE 'NO' END AS economic_weaker_section,
                   CASE WHEN a.is_trainee_dual_mode = true THEN 'YES' ELSE 'NO' END AS is_trainee_dual_mode
            FROM admissions.iti_admissions a
            LEFT JOIN (SELECT DISTINCT dist_code, dist_name FROM public.dist_mst) d ON TRIM(a.dist_code::text) = TRIM(d.dist_code::text)
            WHERE a.year_of_admission::text = ?::text
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode) && !distCode.isEmpty()) {
            sql.append(" AND TRIM(a.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }
        if (itiCode != null && !"All".equalsIgnoreCase(itiCode) && !itiCode.isEmpty()) {
            sql.append(" AND TRIM(a.iti_code::text) = TRIM(?::text)");
            params.add(itiCode);
        }

        sql.append(" ORDER BY a.name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new StudentListResponse(
                rs.getString("admission_no"),
                rs.getString("trade_code"),
                rs.getString("ssc_hall_ticket"),
                rs.getString("name"),
                rs.getString("father_name"),
                rs.getString("mother_name"),
                rs.getString("date_of_birth"),
                rs.getString("mobile_no"),
                rs.getString("email"),
                rs.getString("shift"),
                rs.getString("unit"),
                rs.getString("pwd_category"),
                rs.getString("economic_weaker_section"),
                rs.getString("is_trainee_dual_mode")
        ), params.toArray());
    }

    // 17. Trade Wise Report
    @Override
    public List<TradeWiseReportResponse> getTradeWiseReport(String year, String distCode, String itiType) {
        StringBuilder sql = new StringBuilder("""
            SELECT tm.trade_name, tm.trade_code::text AS trade_code,
                   COUNT(*) AS total_strength,
                   COUNT(*) FILTER (WHERE a.adm_num IS NOT NULL) AS filled,
                   COUNT(*) - COUNT(*) FILTER (WHERE a.adm_num IS NOT NULL) AS vacant
            FROM public.ititrade_master tm
            CROSS JOIN public.iti i
            LEFT JOIN admissions.iti_admissions a ON a.iti_code = i.iti_code
                AND a.trade_code = tm.trade_code
                AND a.year_of_admission::text = ?::text
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND TRIM(i.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }
        if (itiType != null && !"All".equalsIgnoreCase(itiType)) {
            sql.append(" AND i.govt = ?");
            params.add(itiType);
        }

        sql.append(" GROUP BY tm.trade_name, tm.trade_code ORDER BY tm.trade_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new TradeWiseReportResponse(
                rs.getString("trade_name"),
                rs.getString("trade_code"),
                rs.getInt("total_strength"),
                rs.getInt("filled"),
                rs.getInt("vacant")
        ), params.toArray());
    }

    // 18. Today Schedule ITIs
    @Override
    public List<TodayScheduleResponse> getTodaySchedule() {
        String sql = """
            SELECT d.dist_name, i.iti_name,
                   a.merit_from, a.merit_to, a.cal_date, a.cal_time
            FROM public.admission_timings a
            JOIN public.iti i ON a.iti_code = i.iti_code
            JOIN public.dist_mst d ON i.dist_code = d.dist_code
            WHERE a.cal_date::text = CURRENT_DATE::text
            ORDER BY d.dist_name, i.iti_name
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TodayScheduleResponse(
                rs.getString("dist_name"),
                rs.getString("iti_name"),
                rs.getInt("merit_from"),
                rs.getInt("merit_to"),
                rs.getString("cal_date"),
                rs.getString("cal_time")
        ));
    }

    // 19. District Schedule
    @Override
    public List<DistrictScheduleResponse> getDistrictSchedule(String distCode) {
        String sql = """
            SELECT d.dist_name, i.iti_name, tm.trade_name,
                   a.merit_from, a.merit_to, a.cal_date, a.cal_time, a.phase
            FROM public.admission_timings a
            JOIN public.iti i ON a.iti_code = i.iti_code
            JOIN public.dist_mst d ON i.dist_code = d.dist_code
            LEFT JOIN public.ititrade_master tm ON a.minqul = tm.trade_code::text
            WHERE TRIM(i.dist_code::text) = TRIM(?::text)
            ORDER BY i.iti_name, a.phase
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new DistrictScheduleResponse(
                rs.getString("dist_name"),
                rs.getString("iti_name"),
                rs.getString("trade_name"),
                rs.getInt("merit_from"),
                rs.getInt("merit_to"),
                rs.getString("cal_date"),
                rs.getString("cal_time"),
                rs.getString("phase")
        ), distCode);
    }

    // 20. All Resource Role
    @Override
    public List<AllResourceRoleResponse> getAllResourceRoles() {
        String sql = """
            SELECT 
                   lu.roleid::text as rolename, 
                   lu.username, 
                   d.dist_name, 
                   i.iti_name,
                   i.mobile, 
                   i.email
            FROM public.login_users lu
            LEFT JOIN public.iti i ON lu.ins_code = i.iti_code
            LEFT JOIN public.dist_mst d ON i.dist_code = d.dist_code
            ORDER BY lu.roleid, lu.username
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new AllResourceRoleResponse(
                rs.getString("rolename"),
                rs.getString("username"),
                rs.getString("dist_name"),
                rs.getString("iti_name"),
                rs.getString("mobile"),
                rs.getString("email")
        ));
    }

    // 21. Applicant Address With Mobile
    @Override
    public List<ApplicantMobileAddressResponse> getApplicantMobileAddress(String year, String distCode) {
        StringBuilder sql = new StringBuilder("""
            SELECT a.regid, a.name, a.fname, a.phno, a.addr, d.dist_name
            FROM public.application a
            LEFT JOIN public.iti i ON a.user_id = i.iti_code
            LEFT JOIN public.dist_mst d ON i.dist_code = d.dist_code
            WHERE a.year::text = ?::text
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode) && !distCode.isEmpty()) {
            sql.append(" AND TRIM(i.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }

        sql.append(" ORDER BY a.name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ApplicantMobileAddressResponse(
                rs.getString("regid"),
                rs.getString("name"),
                rs.getString("fname"),
                rs.getString("phno"),
                rs.getString("addr"),
                rs.getString("dist_name")
        ), params.toArray());
    }

    // 22. District Wise Application Count
    @Override
    public List<DistrictWiseApplicationCountResponse> getDistrictWiseApplicationCount(String year) {
        String sql = """
            SELECT d.dist_name AS "District Name",
                   COUNT(a.regid) AS "Total Applications",
                   COUNT(a.regid) FILTER (WHERE a.app_status = 'A') AS "Approved",
                   COUNT(a.regid) FILTER (WHERE a.app_status = 'R') AS "Rejected",
                   COUNT(a.regid) FILTER (WHERE a.app_status IS NULL OR a.app_status NOT IN ('A', 'R')) AS "Unverified"
            FROM (SELECT DISTINCT dist_code, dist_name FROM public.dist_mst) d
            LEFT JOIN public.iti i ON d.dist_code = i.dist_code
            LEFT JOIN public.application a ON i.iti_code = a.user_id AND a.year::text = ?::text
            GROUP BY d.dist_name
            ORDER BY d.dist_name
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new DistrictWiseApplicationCountResponse(
                rs.getString("District Name"),
                rs.getInt("Total Applications"),
                rs.getInt("Approved"),
                rs.getInt("Rejected"),
                rs.getInt("Unverified")
        ), year);
    }

    private String str(Object o) {
        return o != null ? o.toString() : "";
    }

    private String val(Object o) {
        if (o == null) return "0";
        if (o instanceof Number) return String.valueOf(((Number) o).intValue());
        return o.toString();
    }
}
