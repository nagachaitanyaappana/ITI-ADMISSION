package com.server.backend.service.Reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.Reports.AdmissionReportResponse;
import com.server.backend.DTO.Reports.ApiDashboardResponse;
import com.server.backend.DTO.Reports.ApplicantCountResponse;
import com.server.backend.DTO.Reports.CollegeWiseOpenSeatsResponse;
import com.server.backend.DTO.Reports.DscFullReportResponse;
import com.server.backend.DTO.Reports.ITIAdmissionsReportResponse;
import com.server.backend.DTO.Reports.MetadataResponse;
import com.server.backend.DTO.Reports.OpenSeatsAbstractResponse;
import com.server.backend.DTO.Reports.PhaseWiseReportResponse;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.AdmissionDetail;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.AppliedIti;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.MeritListDetail;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.RegistrationDetail;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.SscMarksDetail;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse.VerifiedDetail;
import com.server.backend.DTO.Reports.TradeDurationSeatsResponse;

@Service
public class ReportServiceImpl implements ReportService {

    private final JdbcTemplate jdbcTemplate;

    public ReportServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MetadataResponse getMetadata() {
        List<String> years = jdbcTemplate.queryForList(
                "SELECT DISTINCT year_of_admission FROM admissions.iti_admissions ORDER BY year_of_admission DESC",
                String.class);
        List<String> phases = jdbcTemplate.queryForList(
                "SELECT DISTINCT phase::text FROM admissions.iti_admissions WHERE phase IS NOT NULL ORDER BY phase",
                String.class);
        List<String> castes = jdbcTemplate.queryForList(
                "SELECT DISTINCT caste FROM application WHERE caste IS NOT NULL ORDER BY caste",
                String.class);
        List<String> levels = List.of("CITS", "Dual Mode");
        return new MetadataResponse(years, phases, castes, levels);
    }

    // 1. Phase Wise Abstract
    @Override
    public List<PhaseWiseReportResponse> getPhaseWiseReport(String year) {
        String sql = """
            SELECT d.dist_name,
                   COALESCE(SUM(CASE WHEN a.phase = 1 THEN 1 ELSE 0 END), 0) AS phase_i,
                   COALESCE(SUM(CASE WHEN a.phase = 2 THEN 1 ELSE 0 END), 0) AS phase_ii,
                   COALESCE(SUM(CASE WHEN a.phase = 3 THEN 1 ELSE 0 END), 0) AS phase_iii,
                   COALESCE(SUM(CASE WHEN a.phase = 4 THEN 1 ELSE 0 END), 0) AS phase_iv,
                   COALESCE(SUM(CASE WHEN a.phase = 5 THEN 1 ELSE 0 END), 0) AS phase_v,
                   COUNT(*) AS total,
                   COALESCE(SUM(CASE WHEN a.date_of_admission = CURRENT_DATE THEN 1 ELSE 0 END), 0) AS today
            FROM admissions.iti_admissions a
            JOIN dist_mst d ON d.dist_code = a.dist_code
            WHERE a.year_of_admission = ?
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
            SELECT d.dist_code, d.dist_name,
                   COALESCE(SUM((s.strength ->> '1')::int), 0) 
                   + COALESCE(SUM((s.strength ->> '2')::int), 0)
                   + COALESCE(SUM((s.strength ->> '3')::int), 0)
                   + COALESCE(SUM((s.strength ->> '4')::int), 0)
                   + COALESCE(SUM((s.strength ->> '5')::int), 0) AS no_of_seats,
                   COALESCE(SUM((s.strength_fill ->> '1')::int), 0)
                   + COALESCE(SUM((s.strength_fill ->> '2')::int), 0)
                   + COALESCE(SUM((s.strength_fill ->> '3')::int), 0)
                   + COALESCE(SUM((s.strength_fill ->> '4')::int), 0)
                   + COALESCE(SUM((s.strength_fill ->> '5')::int), 0) AS fill,
                   COALESCE(SUM((s.strength_vacant ->> '1')::int), 0)
                   + COALESCE(SUM((s.strength_vacant ->> '2')::int), 0)
                   + COALESCE(SUM((s.strength_vacant ->> '3')::int), 0)
                   + COALESCE(SUM((s.strength_vacant ->> '4')::int), 0)
                   + COALESCE(SUM((s.strength_vacant ->> '5')::int), 0) AS vacant
            FROM iti_seatmatrix s
            JOIN iti i ON i.iti_code = s.iti_code
            JOIN dist_mst d ON d.dist_code = i.dist_code
            WHERE s.year = ?
            GROUP BY d.dist_code, d.dist_name
            ORDER BY d.dist_name
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new OpenSeatsAbstractResponse(
                rs.getString("dist_code"),
                rs.getString("dist_name"),
                rs.getInt("no_of_seats"),
                rs.getInt("fill"),
                rs.getInt("vacant")
        ), year);
    }

    // 3. College Wise Open Seats
    @Override
    public List<CollegeWiseOpenSeatsResponse> getCollegeWiseOpenSeats(String year, String distCode) {
        String sql = """
            SELECT i.iti_code, i.iti_name,
                   COALESCE(SUM((s.strength ->> '1')::int), 0)
                   + COALESCE(SUM((s.strength ->> '2')::int), 0)
                   + COALESCE(SUM((s.strength ->> '3')::int), 0)
                   + COALESCE(SUM((s.strength ->> '4')::int), 0)
                   + COALESCE(SUM((s.strength ->> '5')::int), 0) AS no_of_seats,
                   COALESCE(SUM((s.strength_fill ->> '1')::int), 0)
                   + COALESCE(SUM((s.strength_fill ->> '2')::int), 0)
                   + COALESCE(SUM((s.strength_fill ->> '3')::int), 0)
                   + COALESCE(SUM((s.strength_fill ->> '4')::int), 0)
                   + COALESCE(SUM((s.strength_fill ->> '5')::int), 0) AS fill,
                   COALESCE(SUM((s.strength_vacant ->> '1')::int), 0)
                   + COALESCE(SUM((s.strength_vacant ->> '2')::int), 0)
                   + COALESCE(SUM((s.strength_vacant ->> '3')::int), 0)
                   + COALESCE(SUM((s.strength_vacant ->> '4')::int), 0)
                   + COALESCE(SUM((s.strength_vacant ->> '5')::int), 0) AS vacant
            FROM iti_seatmatrix s
            JOIN iti i ON i.iti_code = s.iti_code
            WHERE s.year = ? AND i.dist_code = ?
            GROUP BY i.iti_code, i.iti_name
            ORDER BY i.iti_name
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CollegeWiseOpenSeatsResponse(
                rs.getString("iti_code"),
                rs.getString("iti_name"),
                rs.getInt("no_of_seats"),
                rs.getInt("fill"),
                rs.getInt("vacant")
        ), year, distCode);
    }

    // 4. Trade Duration Seats Abstract
    @Override
    public List<TradeDurationSeatsResponse> getTradeDurationSeats(String year, String durationMonths, String itiType) {
        String sql = """
            SELECT tm.trade_code, tm.trade_name,
                   COALESCE(SUM(t.strength), 0) AS strength,
                   COALESCE(SUM(t.strength_fill), 0) AS fill,
                   COALESCE(SUM(t.strength_vacant), 0) AS vacant
            FROM ititrade t
            JOIN ititrade_master tm ON tm.trade_short = t.trade_short
            JOIN iti i ON i.iti_code = t.iti_code
            WHERE (tm.durationyrs * 12)::text = ?
              AND i.govt = ?
            GROUP BY tm.trade_code, tm.trade_name
            ORDER BY tm.trade_name
            """;
        if ("All".equalsIgnoreCase(durationMonths)) {
            sql = """
                SELECT tm.trade_code, tm.trade_name,
                       COALESCE(SUM(t.strength), 0) AS strength,
                       COALESCE(SUM(t.strength_fill), 0) AS fill,
                       COALESCE(SUM(t.strength_vacant), 0) AS vacant
                FROM ititrade t
                JOIN ititrade_master tm ON tm.trade_short = t.trade_short
                JOIN iti i ON i.iti_code = t.iti_code
                WHERE i.govt = ?
                GROUP BY tm.trade_code, tm.trade_name
                ORDER BY tm.trade_name
                """;
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                int str = rs.getInt("strength");
                int fill = rs.getInt("fill");
                double pct = str > 0 ? (fill * 100.0 / str) : 0;
                return new TradeDurationSeatsResponse(
                        rs.getString("trade_code"),
                        rs.getString("trade_name"),
                        str, fill, str - fill,
                        Math.round(pct * 100.0) / 100.0
                );
            }, itiType);
        }
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int str = rs.getInt("strength");
            int fill = rs.getInt("fill");
            double pct = str > 0 ? (fill * 100.0 / str) : 0;
            return new TradeDurationSeatsResponse(
                    rs.getString("trade_code"),
                    rs.getString("trade_name"),
                    str, fill, str - fill,
                    Math.round(pct * 100.0) / 100.0
            );
        }, durationMonths, itiType);
    }

    // 5. Admission Report (Trade wise - boys/girls)
    @Override
    public List<AdmissionReportResponse> getAdmissionReport(String year, String caste, String pwd) {
        StringBuilder sql = new StringBuilder("""
            SELECT tm.trade_name,
                   COALESCE(SUM(CASE WHEN a.gender = 'Male' THEN 1 ELSE 0 END), 0) AS boys,
                   COALESCE(SUM(CASE WHEN a.gender = 'Female' THEN 1 ELSE 0 END), 0) AS girls,
                   COUNT(*) AS total
            FROM admissions.iti_admissions a
            JOIN iti i ON i.iti_code = a.iti_code
            JOIN ititrade t ON t.iti_code = a.iti_code AND t.trade_code = a.trade_code
            JOIN ititrade_master tm ON tm.trade_short = t.trade_short
            WHERE a.year_of_admission = ?
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);

        if (caste != null && !"All".equalsIgnoreCase(caste)) {
            sql.append(" AND a.caste = ?");
            params.add(caste);
        }
        if ("Yes".equalsIgnoreCase(pwd)) {
            sql.append(" AND a.pwd_category IS NOT NULL AND a.pwd_category != ''");
        } else if ("No".equalsIgnoreCase(pwd)) {
            sql.append(" AND (a.pwd_category IS NULL OR a.pwd_category = '')");
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
        // Govt & Pvt combined
        String sqlAll = """
            SELECT d.dist_name, COUNT(*) AS cnt
            FROM application a
            JOIN dist_mst d ON d.dist_code = a.dist_code
            WHERE a.year = ? AND a.phase -> ? = 'true'
            GROUP BY d.dist_name
            ORDER BY d.dist_name
            """;
        List<ApplicantCountResponse.DistCountRow> govtPvt = jdbcTemplate.query(
                sqlAll, (rs, rn) -> new ApplicantCountResponse.DistCountRow(
                        rs.getString("dist_name"), rs.getInt("cnt")),
                year, phase);

        // Govt only
        String sqlGovt = """
            SELECT d.dist_name, COUNT(*) AS cnt
            FROM application a
            JOIN dist_mst d ON d.dist_code = a.dist_code
            JOIN iti i ON i.dist_code = a.dist_code
            WHERE a.year = ? AND a.phase -> ? = 'true' AND i.govt = 'G'
            GROUP BY d.dist_name
            ORDER BY d.dist_name
            """;
        List<ApplicantCountResponse.DistCountRow> govt = jdbcTemplate.query(
                sqlGovt, (rs, rn) -> new ApplicantCountResponse.DistCountRow(
                        rs.getString("dist_name"), rs.getInt("cnt")),
                year, phase);

        // Private only
        String sqlPvt = """
            SELECT d.dist_name, COUNT(*) AS cnt
            FROM application a
            JOIN dist_mst d ON d.dist_code = a.dist_code
            JOIN iti i ON i.dist_code = a.dist_code
            WHERE a.year = ? AND a.phase -> ? = 'true' AND i.govt = 'P'
            GROUP BY d.dist_name
            ORDER BY d.dist_name
            """;
        List<ApplicantCountResponse.DistCountRow> pvt = jdbcTemplate.query(
                sqlPvt, (rs, rn) -> new ApplicantCountResponse.DistCountRow(
                        rs.getString("dist_name"), rs.getInt("cnt")),
                year, phase);

        int govtPvtTotal = govtPvt.stream().mapToInt(ApplicantCountResponse.DistCountRow::getCount).sum();
        int govtTotal = govt.stream().mapToInt(ApplicantCountResponse.DistCountRow::getCount).sum();
        int pvtTotal = pvt.stream().mapToInt(ApplicantCountResponse.DistCountRow::getCount).sum();

        return new ApplicantCountResponse(govtPvt, govt, pvt, govtPvtTotal, govtTotal, pvtTotal);
    }

    // 7. ITI Admissions Report
    @Override
    public List<ITIAdmissionsReportResponse> getITIAdmissionsReport(String year, String distCode, String govt,
            String caste, String gender, String ncvtScvt, String limitRows) {
        StringBuilder sql = new StringBuilder("""
            SELECT a.adm_num, a.name, a.ssc_regno, a.year_of_admission
            FROM admissions.iti_admissions a
            JOIN iti i ON i.iti_code = a.iti_code
            WHERE a.year_of_admission = ?
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND a.dist_code = ?");
            params.add(distCode);
        }
        if (govt != null && !"All".equalsIgnoreCase(govt)) {
            sql.append(" AND i.govt = ?");
            params.add("Govt".equalsIgnoreCase(govt) ? "G" : "P");
        }
        if (caste != null && !"All".equalsIgnoreCase(caste)) {
            sql.append(" AND a.caste = ?");
            params.add(caste);
        }
        if (gender != null && !"All".equalsIgnoreCase(gender)) {
            sql.append(" AND a.gender = ?");
            params.add(gender);
        }
        if (ncvtScvt != null && !"All".equalsIgnoreCase(ncvtScvt)) {
            sql.append(" AND a.type_admission = ?");
            params.add(ncvtScvt);
        }

        sql.append(" ORDER BY a.name");

        if (limitRows != null && !"All".equalsIgnoreCase(limitRows)) {
            sql.append(" LIMIT ?");
            params.add(Integer.parseInt(limitRows));
        } else {
            sql.append(" LIMIT 500");
        }

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ITIAdmissionsReportResponse(
                rs.getString("adm_num"),
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
        String itiSql = "SELECT iti_name FROM iti WHERE iti_code = ?";
        String tradeSql = "SELECT trade_name FROM ititrade_master WHERE trade_code::text = ?";
        try {
            response.setItiName(jdbcTemplate.queryForObject(itiSql, String.class, itiCode));
        } catch (Exception e) {
            response.setItiName(itiCode);
        }
        try {
            response.setTradeName(jdbcTemplate.queryForObject(tradeSql, String.class, tradeCode));
        } catch (Exception e) {
            response.setTradeName(tradeCode);
        }
        response.setSession(year + " Phase " + phase);

        // Get seat matrix info per category
        String seatSql = """
            SELECT res_category AS category_code,
                   COALESCE((strength ->> ?)::int, 0) AS strength,
                   COALESCE((strength_fill ->> ?)::int, 0) AS filled
            FROM iti_seatmatrix
            WHERE iti_code = ? AND trade_code::text = ? AND year = ? AND phase::text = ?
            """;
        List<Map<String, Object>> catRows = jdbcTemplate.queryForList(seatSql, phase, phase, itiCode, tradeCode, year, phase);

        List<DscFullReportResponse.CategoryGroup> categories = new ArrayList<>();
        for (Map<String, Object> catRow : catRows) {
            String categoryCode = (String) catRow.get("category_code");
            int strength = ((Number) catRow.get("strength")).intValue();
            int filled = ((Number) catRow.get("filled")).intValue();

            // Get candidates for this category
            String candSql = """
                SELECT a.adm_num, a.name, a.fname, a.gender, a.dob::text, a.caste,
                       COALESCE(r.rank, '') AS rank
                FROM admissions.iti_admissions a
                LEFT JOIN ranks r ON r.regid = a.regid::int AND r.phase::text = ?
                WHERE a.iti_code = ? AND a.trade_code::text = ? 
                  AND a.year_of_admission = ? AND a.res_category = ?
                ORDER BY a.name
                """;
            List<DscFullReportResponse.CandidateRow> candidates = new ArrayList<>();
            try {
                List<Map<String, Object>> candRows = jdbcTemplate.queryForList(candSql, phase, itiCode, tradeCode, year, categoryCode);
                int slNo = 1;
                for (Map<String, Object> cand : candRows) {
                    candidates.add(new DscFullReportResponse.CandidateRow(
                            slNo++,
                            (String) cand.get("rank"),
                            (String) cand.get("adm_num"),
                            (String) cand.get("name"),
                            (String) cand.get("fname"),
                            (String) cand.get("gender"),
                            cand.get("dob") != null ? cand.get("dob").toString() : "",
                            (String) cand.get("caste")
                    ));
                }
            } catch (Exception e) {
                // No candidates
            }

            categories.add(new DscFullReportResponse.CategoryGroup(
                    categoryCode, strength, filled, strength - filled, candidates
            ));
        }

        response.setCategories(categories);
        return response;
    }

    // 9. API Dashboard
    @Override
    public List<ApiDashboardResponse> getApiDashboard(String year) {
        String sql = """
            SELECT d.dist_name,
                   COUNT(*) AS total,
                   COALESCE(SUM(CASE WHEN a.data_flag = '1' THEN 1 ELSE 0 END), 0) AS success,
                   COALESCE(SUM(CASE WHEN a.data_flag = '2' THEN 1 ELSE 0 END), 0) AS verified,
                   COALESCE(SUM(CASE WHEN a.data_flag IS NULL OR a.data_flag = '0' THEN 1 ELSE 0 END), 0) AS to_be_verified,
                   COALESCE(SUM(CASE WHEN a.data_flag = '3' THEN 1 ELSE 0 END), 0) AS to_be_updated,
                   0 AS phone_duplicate_records,
                   0 AS aadhar_duplicate_records,
                   0 AS email_duplicate_records
            FROM application a
            JOIN dist_mst d ON d.dist_code = a.dist_code
            WHERE a.year = ?
            GROUP BY d.dist_name
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
        ), year);
    }

    // 10. Student Complete Details
    @Override
    public StudentCompleteDetailsResponse getStudentCompleteDetails(String regid, String admNum) {
        StudentCompleteDetailsResponse response = new StudentCompleteDetailsResponse();

        // Find regid if adm_num provided
        if (admNum != null && !admNum.isEmpty()) {
            try {
                String findSql = "SELECT regid FROM admissions.iti_admissions WHERE adm_num = ?";
                regid = jdbcTemplate.queryForObject(findSql, String.class, admNum);
            } catch (Exception e) {
                return response;
            }
        }

        String finalRegid = regid;

        // 1. Registration details from application table
        String regSql = """
            SELECT a.name, a.regid::text AS registration_id, a.dob::text AS date_of_birth,
                   a.ssc_regno AS ssc_ht_no, a.fname AS father_name, a.mname AS mother_name,
                   a.addr AS address, a.phno::text AS phone_no, a.gender, a.caste,
                   CASE WHEN a.ssc_passed THEN 'Yes' ELSE 'No' END AS ssc_passed,
                   CASE WHEN a.phc THEN 'Yes' ELSE 'No' END AS phc,
                   a.ssc_year AS ssc_pass_year,
                   a.phase -> ? AS registered_phase,
                   a.entry_date::text AS registration_date,
                   a.verified_date::text AS verified_date
            FROM application a
            WHERE a.regid::text = ?
            """;
        try {
            List<Map<String, Object>> regRows = jdbcTemplate.queryForList(regSql, "1", finalRegid);
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
            }
        } catch (Exception e) {
            // Not found
        }

        // 2. SSC Marks from cand_marks
        String marksSql = """
            SELECT ssc_first_lang_marks, ssc_second_lang_marks, ssc_eng_marks,
                   ssc_math_marks, ssc_sci_marks, ssc_social_marks, ssc_tot_marks
            FROM cand_marks WHERE regid::text = ?
            """;
        try {
            List<Map<String, Object>> marksRows = jdbcTemplate.queryForList(marksSql, finalRegid);
            if (!marksRows.isEmpty()) {
                Map<String, Object> m = marksRows.get(0);
                response.setSscMarks(new SscMarksDetail(
                        val(m.get("ssc_first_lang_marks")), val(m.get("ssc_second_lang_marks")),
                        val(m.get("ssc_eng_marks")), val(m.get("ssc_math_marks")),
                        val(m.get("ssc_sci_marks")), val(m.get("ssc_social_marks")),
                        val(m.get("ssc_tot_marks"))
                ));
            }
        } catch (Exception e) {
            // Not found
        }

        // 3. Applied ITIs from student_trade_sel
        String appliedSql = """
            SELECT s.iti_code, i.iti_name, 
                   s.phase -> ? AS phase, s.year AS admissions_year
            FROM student_trade_sel s
            LEFT JOIN iti i ON i.iti_code = s.iti_code
            WHERE s.regid::text = ?
            """;
        try {
            List<Map<String, Object>> appliedRows = jdbcTemplate.queryForList(appliedSql, "1", finalRegid);
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

        // 4. Verified details - same as registration for now (from iti_admissions)
        String verSql = """
            SELECT a.name, a.regid AS registration_id, a.dob::text AS date_of_birth,
                   a.ssc_regno AS ssc_ht_no, a.fname AS father_name, a.mname AS mother_name,
                   a.addr AS address, a.phno::text AS phone_no, a.gender, a.caste,
                   CASE WHEN a.ssc_passed THEN 'Yes' ELSE 'No' END AS ssc_passed,
                   CASE WHEN a.phc THEN 'Yes' ELSE 'No' END AS phc,
                   a.ssc_year AS ssc_pass_year,
                   a.phase::text AS registered_phase,
                   a.date_of_admission::text AS registration_date
            FROM admissions.iti_admissions a
            WHERE a.regid = ?
            """;
        try {
            List<Map<String, Object>> verRows = jdbcTemplate.queryForList(verSql, finalRegid);
            if (!verRows.isEmpty()) {
                Map<String, Object> v = verRows.get(0);
                response.setVerified(new VerifiedDetail(
                        str(v.get("name")), str(v.get("registration_id")), str(v.get("date_of_birth")),
                        str(v.get("ssc_ht_no")), str(v.get("father_name")), str(v.get("mother_name")),
                        str(v.get("address")), str(v.get("phone_no")), str(v.get("gender")),
                        str(v.get("caste")), str(v.get("ssc_passed")), str(v.get("phc")),
                        str(v.get("ssc_pass_year")), str(v.get("registered_phase")),
                        str(v.get("registration_date"))
                ));
            }
        } catch (Exception e) {
            // Not found
        }

        // 5. Merit list
        String meritSql = """
            SELECT d.dist_name, i.iti_name, r.rank, r.phase, r.qual AS qualification
            FROM ranks r
            LEFT JOIN dist_mst d ON d.dist_code = r.dist_code
            LEFT JOIN iti i ON i.iti_code = r.iti_code
            WHERE r.regid = ?
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
                   a.year_of_admission, a.phase::text, a.date_of_admission::text,
                   a.phno::text AS phone_number
            FROM admissions.iti_admissions a
            LEFT JOIN dist_mst d ON d.dist_code = a.dist_code
            LEFT JOIN iti i ON i.iti_code = a.iti_code
            LEFT JOIN ititrade t ON t.iti_code = a.iti_code AND t.trade_code = a.trade_code
            LEFT JOIN ititrade_master tm ON tm.trade_short = t.trade_short
            WHERE a.regid = ?
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

    private String str(Object o) {
        return o != null ? o.toString() : "";
    }

    private String val(Object o) {
        if (o == null) return "0";
        if (o instanceof Number) return String.valueOf(((Number) o).intValue());
        return o.toString();
    }
}