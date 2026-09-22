package com.server.backend.service.Reports;

import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.AdmissionReportDetailResponse;
import com.server.backend.DTO.NotAdmittedStudentResponse;
import com.server.backend.DTO.AdmissionReportResponse;
import com.server.backend.DTO.AllResourceRoleResponse;
import com.server.backend.DTO.ApiDashboardResponse;
import com.server.backend.DTO.ApplicantMobileAddressResponse;
import com.server.backend.DTO.ApplicantCountDistrictResponse;
import com.server.backend.DTO.ApplicantReportResponse;
import com.server.backend.DTO.CasteWiseAdmissionsResponse;
import com.server.backend.DTO.DistrictScheduleResponse;
import com.server.backend.DTO.DistrictWiseApplicationCountResponse;
import com.server.backend.DTO.DscFullReportResponse;
import com.server.backend.DTO.GovtPvtSeatsAbstractResponse;
import com.server.backend.DTO.ITIAdmissionsReportResponse;
import com.server.backend.DTO.ItiWiseStatusResponse;
import com.server.backend.DTO.OpenSeatsAbstractResponse;
import com.server.backend.DTO.PhaseWiseReportResponse;
import com.server.backend.DTO.ShiftUnitResponse;
import com.server.backend.DTO.StateDashboardResponse;
import com.server.backend.DTO.StrengthFilledSeatsResponse;
import com.server.backend.DTO.StudentCompleteDetailsResponse;
import com.server.backend.DTO.StudentCompleteDetailsResponse.AdmissionDetail;
import com.server.backend.DTO.StudentCompleteDetailsResponse.AppliedIti;
import com.server.backend.DTO.StudentCompleteDetailsResponse.MeritListDetail;
import com.server.backend.DTO.StudentCompleteDetailsResponse.RegistrationDetail;
import com.server.backend.DTO.StudentCompleteDetailsResponse.SscMarksDetail;
import com.server.backend.DTO.StudentCompleteDetailsResponse.VerifiedDetail;
import com.server.backend.DTO.TodayScheduleResponse;
import com.server.backend.DTO.TradeDurationSeatsResponse;
import com.server.backend.DTO.TradeWiseReportResponse;
import com.server.backend.DTO.TradeWiseVacantResponse;
//import com.server.backend.DTO.VerifiedApplicationCountResponse;
import com.server.backend.DTO.VerifiedApplicationCountReportResponse;
import com.server.backend.DTO.DscOptionsResponse;
import com.server.backend.DTO.CurrentAdmissionPhaseResponse;

@Service
public class ReportServiceImpl implements ReportService {

    private final JdbcTemplate jdbcTemplate;

    public ReportServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. ITI Wise Status Report
    @Override
    public List<ItiWiseStatusResponse> getItiWiseStatus(String year, String distCode, String itiCode, int page, int size) {
        String effectiveYear = (year != null && !year.isEmpty()) ? year : String.valueOf(Year.now().getValue());
        
        StringBuilder sql = new StringBuilder("""
            WITH iti_data AS (
                SELECT
                    a.iti_code,
                    COUNT(*) AS total,
                    COUNT(*) FILTER (WHERE a.rec_status = 'S') AS success,
                    COUNT(*) FILTER (WHERE a.rec_status = 'N') AS pending_sid,
                    COUNT(*) FILTER (WHERE a.rec_status = 'D') AS verified,
                    COUNT(*) FILTER (WHERE a.rec_status = 'E') AS to_be_verified,
                    COUNT(*) FILTER (WHERE a.rec_status IS NULL OR a.rec_status = '') AS to_be_updated,
                    COUNT(DISTINCT CASE WHEN phone_cnt > 1 AND a.phno IS NOT NULL THEN a.phno END) AS phone_duplicate_records,
                    COUNT(DISTINCT CASE WHEN aadhar_cnt > 1 AND a.adarno IS NOT NULL AND a.adarno != '' THEN a.adarno END) AS aadhar_duplicate_records,
                    COUNT(DISTINCT CASE WHEN email_cnt > 1 AND a.email_id IS NOT NULL AND a.email_id != '' THEN a.email_id END) AS email_duplicate_records
                FROM (
                    SELECT a.*,
                        COUNT(*) OVER (PARTITION BY a.iti_code, a.phno) AS phone_cnt,
                        COUNT(*) OVER (PARTITION BY a.iti_code, a.adarno) AS aadhar_cnt,
                        COUNT(*) OVER (PARTITION BY a.iti_code, a.email_id) AS email_cnt
                    FROM admissions.iti_admissions a
                    WHERE a.year_of_admission = ?
                ) a
                GROUP BY a.iti_code
            )
            SELECT d.dist_name, i.iti_name, i.iti_code,
                   COALESCE(id.total, 0) AS total,
                   COALESCE(id.success, 0) AS success,
                   COALESCE(id.pending_sid, 0) AS pending_sid,
                   COALESCE(id.verified, 0) AS verified,
                   COALESCE(id.to_be_verified, 0) AS to_be_verified,
                   COALESCE(id.to_be_updated, 0) AS to_be_updated,
                   COALESCE(id.phone_duplicate_records, 0) AS phone_duplicate_records,
                   COALESCE(id.aadhar_duplicate_records, 0) AS aadhar_duplicate_records,
                   COALESCE(id.email_duplicate_records, 0) AS email_duplicate_records
            FROM (SELECT DISTINCT dist_code, dist_name FROM public.dist_mst) d
            JOIN public.iti i ON d.dist_code = i.dist_code
            LEFT JOIN iti_data id ON i.iti_code = id.iti_code
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        params.add(effectiveYear);

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND TRIM(d.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }
        if (itiCode != null && !"All".equalsIgnoreCase(itiCode) && !itiCode.isEmpty()) {
            sql.append(" AND TRIM(i.iti_code::text) = TRIM(?::text)");
            params.add(itiCode);
        }

        sql.append(" ORDER BY d.dist_name, i.iti_name LIMIT ? OFFSET ?");
        params.add(size);
        params.add((long) page * size);

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

    // 2. Applicant Report by Phase
    @Override
    public List<ApplicantReportResponse> getApplicantReportByPhase(String phase, String year, String itiCode, String distCode, int page, int size) {
        StringBuilder sql = new StringBuilder("""
            SELECT sa.ssc_regno, sa.phno AS mobile_no, sa.regid AS reg_id,
                   sa.name, sa.fname AS father_name, sa.mname AS mother_name
            FROM public.student_application sa
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

        sql.append(" ORDER BY sa.name LIMIT ? OFFSET ?");
        params.add(size);
        params.add((long) page * size);

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ApplicantReportResponse(
                rs.getString("ssc_regno"),
                rs.getString("mobile_no"),
                rs.getString("reg_id"),
                rs.getString("name"),
                rs.getString("father_name"),
                rs.getString("mother_name")
        ), params.toArray());
    }

    // 3. Admission Report (Trade wise - for role 1)
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

    // 3a. Admission Report (ITI - candidate detail for role 4)
    @Override
    public List<AdmissionReportDetailResponse> getAdmissionReportDetails(int page, int size) {
        int safeSize = Math.min(size, 10000);
        int offset = page * safeSize;
        String sql = "SELECT adm_num, ssc_regno, name, fname, mname, TO_CHAR(dob, 'DD-MM-YYYY') AS dob, phno, email_id, shift, unit, CASE WHEN pwd_category = '1' THEN 'Blind' WHEN pwd_category = '2' THEN 'Deaf' WHEN pwd_category = '3' THEN 'Motor Disability' WHEN pwd_category = '4' THEN 'Mental Disability' ELSE 'NA' END AS pwd_category, CASE WHEN economic_weaker_section = true THEN 'YES' ELSE 'NO' END AS economic_weaker_section, CASE WHEN is_trainee_dual_mode = true THEN 'YES' ELSE 'NO' END AS is_trainee_dual_mode FROM admissions.iti_admissions ORDER BY adm_num LIMIT " + safeSize + " OFFSET " + offset;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new AdmissionReportDetailResponse(
                rs.getString("adm_num"),
                rs.getString("ssc_regno"),
                rs.getString("name"),
                rs.getString("fname"),
                rs.getString("mname"),
                rs.getString("dob"),
                rs.getString("phno"),
                rs.getString("email_id"),
                rs.getString("shift"),
                rs.getString("unit"),
                rs.getString("pwd_category"),
                rs.getString("economic_weaker_section"),
                rs.getString("is_trainee_dual_mode")
        ));
    }

    // 4. DSC Full Report
    @Override
    public DscFullReportResponse getDscFullReport(String distCode, String itiCode, String tradeCode,
            String phase, String year, String modeAdm) {
        DscFullReportResponse response = new DscFullReportResponse();

        String itiName = itiCode;
        try {
            String itiSql = "SELECT iti_name FROM public.iti WHERE iti_code = ?";
            itiName = jdbcTemplate.queryForObject(itiSql, String.class, itiCode);
        } catch (Exception e) {
            System.err.println("[ReportServiceImpl] Unexpected error: " + e.getMessage());
        }

        String tradeName = tradeCode;
        int totalStrength = 0;
        try {
            String tradeSql = "SELECT trade_name FROM public.ititrade_master WHERE trade_code = ?";
            tradeName = jdbcTemplate.queryForObject(tradeSql, String.class, Integer.parseInt(tradeCode));
        } catch (Exception e) {
            System.err.println("[ReportServiceImpl] Unexpected error: " + e.getMessage());
        }

        response.setMeta(new DscFullReportResponse.Meta(
            "DSC Report",
            "AUGUST-" + year,
            phase,
            distCode
        ));
        response.setIti(new DscFullReportResponse.ItiInfo(itiCode, itiName));
        response.setTrade(new DscFullReportResponse.TradeInfo(tradeCode, tradeName, totalStrength));

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
               AND (a.mode_adm IS NULL OR UPPER(TRIM(a.mode_adm::text)) = UPPER(TRIM(?::text)))
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
        if (!catRows.isEmpty()) {
            for (Map<String, Object> cat : catRows) {
                String catCode = str(cat.get("category_code"));
                int strength = ((Number) cat.get("strength")).intValue();
                int filled = ((Number) cat.get("strength_fill")).intValue();
                totalStrength += strength;
                String normCat = normalizeCategory(catCode);
                List<DscFullReportResponse.CandidateRow> candList = candidatesByCategory.getOrDefault(normCat, new ArrayList<>());
                int vacant = Math.max(0, strength - candList.size());

                for (int i = 0; i < vacant; i++) {
                    candList.add(new DscFullReportResponse.CandidateRow(
                            candList.size() + 1, null, "VACANT", "VACANT", "", "", "", ""));
                }

                categories.add(new DscFullReportResponse.CategoryGroup(catCode, strength, filled, vacant, candList));
            }
        } else if (!candidatesByCategory.isEmpty()) {
            for (Map.Entry<String, List<DscFullReportResponse.CandidateRow>> entry : candidatesByCategory.entrySet()) {
                List<DscFullReportResponse.CandidateRow> candList = entry.getValue();
                categories.add(new DscFullReportResponse.CategoryGroup(entry.getKey(), candList.size(), candList.size(), 0, candList));
                totalStrength += candList.size();
            }
        }

        response.getTrade().setTotalStrength(totalStrength);
        response.setCategories(categories);
        return response;
    }

    private String normalizeCategory(String c) {
        return c != null ? c.replaceAll("[\\s\\-_]", "").toUpperCase() : "UNKNOWN";
    }

    // 5. Caste Wise Admissions Abstract
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

    @Override
    public List<ApplicantCountDistrictResponse> getApplicantCountDistrictWise(String year, String distCode, String govt, String phase) {
        StringBuilder sql = new StringBuilder("""
            SELECT d.dist_code AS "District Code", d.dist_name AS "District Name",
                   COUNT(a.regid) AS "Count"
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

        sql.append(" GROUP BY d.dist_code, d.dist_name ORDER BY d.dist_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ApplicantCountDistrictResponse(
                rs.getString("District Code"),
                rs.getString("District Name"),
                rs.getInt("Count")
        ), params.toArray());
    }

    // 6. Applicant Address With Mobile
    @Override
    public List<ApplicantMobileAddressResponse> getApplicantMobileAddress(String year, String distCode, int page, int size) {
        StringBuilder sql = new StringBuilder("""
            SELECT a.regid, a.name, a.fname, a.mname, a.phno, a.ssc_regno, i.address
            FROM public.application a
            LEFT JOIN public.iti i ON a.user_id = i.iti_code
            LEFT JOIN public.dist_mst d ON i.dist_code = d.dist_code
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();

        if (year != null && !year.isEmpty()) {
            sql.append(" AND a.year::text = ?::text");
            params.add(year);
        }

        if (distCode != null && !"All".equalsIgnoreCase(distCode) && !distCode.isEmpty()) {
            sql.append(" AND i.dist_code::text = ?::text");
            params.add(distCode.trim());
        }

        sql.append(" ORDER BY a.name");
        sql.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add((long) page * size);

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ApplicantMobileAddressResponse(
                rs.getString("ssc_regno"),
                rs.getString("phno"),
                rs.getString("regid"),
                rs.getString("name"),
                rs.getString("fname"),
                rs.getString("mname"),
                rs.getString("address")
        ), params.toArray());
    }

    // 7. API Dashboard (District - ITI level, all districts)
    @Override
    public List<ApiDashboardResponse> getApiDashboard(String year, String distCode) {
        String yearFilter = year != null && !year.isEmpty() ? year : null;
        String distFilter = distCode != null && !"All".equalsIgnoreCase(distCode) ? distCode : null;

        String sql = """
            WITH phone_duplicates AS (
              SELECT phno, iti_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE phno IS NOT NULL
              """ + (yearFilter != null ? " AND year_of_admission = ? " : "") + """
              GROUP BY phno, iti_code HAVING COUNT(*) > 1
            ),
            aadhar_duplicates AS (
              SELECT adarno, iti_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE adarno IS NOT NULL AND adarno != ''
              """ + (yearFilter != null ? " AND year_of_admission = ? " : "") + """
              GROUP BY adarno, iti_code HAVING COUNT(*) > 1
            ),
            email_duplicates AS (
              SELECT email_id, iti_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE email_id IS NOT NULL AND email_id != ''
              """ + (yearFilter != null ? " AND year_of_admission = ? " : "") + """
              GROUP BY email_id, iti_code HAVING COUNT(*) > 1
            )
            SELECT i.iti_code, i.iti_name,
                   COUNT(a.*) AS total,
                   COUNT(*) FILTER (WHERE a.rec_status = 'S') AS success,
                   COUNT(*) FILTER (WHERE a.rec_status = 'N') AS pending_sid,
                   COUNT(*) FILTER (WHERE a.rec_status = 'D') AS verified,
                   COUNT(*) FILTER (WHERE a.rec_status = 'E') AS to_be_verified,
                   COUNT(*) FILTER (WHERE a.rec_status IS NULL OR a.rec_status = '') AS to_be_updated,
                   COUNT(DISTINCT pd.phno) FILTER (WHERE pd.phno IS NOT NULL) AS phone_duplicate_records,
                   COUNT(DISTINCT ad.adarno) FILTER (WHERE ad.adarno IS NOT NULL) AS aadhar_duplicate_records,
                   COUNT(DISTINCT ed.email_id) FILTER (WHERE ed.email_id IS NOT NULL) AS email_duplicate_records
            FROM public.iti i
            LEFT JOIN admissions.iti_admissions a
              ON i.iti_code = a.iti_code
              """ + (yearFilter != null ? " AND a.year_of_admission = ? " : "") + """
            LEFT JOIN phone_duplicates pd ON a.iti_code = pd.iti_code AND a.phno = pd.phno
            LEFT JOIN aadhar_duplicates ad ON a.iti_code = ad.iti_code AND a.adarno = ad.adarno
            LEFT JOIN email_duplicates ed ON a.iti_code = ed.iti_code AND a.email_id = ed.email_id
            """ + (distFilter != null ? "WHERE i.dist_code = ?" : "") + """
            GROUP BY i.iti_code, i.iti_name
            ORDER BY i.iti_name
            """;

        List<Object> params = new ArrayList<>();

        if (yearFilter != null) {
            params.add(yearFilter);
            params.add(yearFilter);
            params.add(yearFilter);
            params.add(yearFilter);
        }
        if (distFilter != null) {
            params.add(distFilter);
        }

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ApiDashboardResponse(
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

    // 8. Verified Application Count
    @Override
    public VerifiedApplicationCountReportResponse getVerifiedApplicationCount(String year, String distCode) {
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

        List<VerifiedApplicationCountReportResponse.VerifiedRow> rows = jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new VerifiedApplicationCountReportResponse.VerifiedRow(
                rs.getString("District Name"),
                rs.getInt("Total Applications"),
                rs.getInt("Approved"),
                rs.getInt("Rejected"),
                rs.getInt("Unverified")
        ), params.toArray());

        VerifiedApplicationCountReportResponse response = new VerifiedApplicationCountReportResponse();
        response.setYear(year);
        response.setDistCode(distCode != null ? distCode : "All");
        response.setData(rows);
        return response;
    }

    // 9. State Dashboard
    @Override
    public List<StateDashboardResponse> getStateDashboard(String year, String govt) {
        String govtFilter = (govt != null && !"All".equalsIgnoreCase(govt)) ? govt : null;
        String yearFilter = (year != null && !year.isEmpty()) ? year : null;

        String sql = """
            WITH phone_duplicates AS (
              SELECT phno, iti_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE phno IS NOT NULL
              """ + (yearFilter != null ? " AND year_of_admission = ? " : "") + """
              GROUP BY phno, iti_code HAVING COUNT(*) > 1
            ),
            aadhar_duplicates AS (
              SELECT adarno, iti_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE adarno IS NOT NULL AND adarno != ''
              """ + (yearFilter != null ? " AND year_of_admission = ? " : "") + """
              GROUP BY adarno, iti_code HAVING COUNT(*) > 1
            ),
            email_duplicates AS (
              SELECT email_id, iti_code, COUNT(*) - 1 AS dup_count
              FROM admissions.iti_admissions
              WHERE email_id IS NOT NULL AND email_id != ''
              """ + (yearFilter != null ? " AND year_of_admission = ? " : "") + """
              GROUP BY email_id, iti_code HAVING COUNT(*) > 1
            ),
            district_data AS (
              SELECT i.dist_code,
                     COUNT(a.*) AS total,
                     COUNT(*) FILTER (WHERE a.rec_status = 'S') AS success,
                     COUNT(*) FILTER (WHERE a.rec_status = 'N') AS pending_sid,
                     COUNT(*) FILTER (WHERE a.rec_status = 'D') AS verified,
                     COUNT(*) FILTER (WHERE a.rec_status = 'E') AS to_be_verified,
                     COUNT(*) FILTER (WHERE a.rec_status IS NULL OR a.rec_status = '') AS to_be_updated,
                     COUNT(DISTINCT pd.phno) FILTER (WHERE pd.phno IS NOT NULL) AS phone_duplicate_records,
                     COUNT(DISTINCT ad.adarno) FILTER (WHERE ad.adarno IS NOT NULL) AS aadhar_duplicate_records,
                     COUNT(DISTINCT ed.email_id) FILTER (WHERE ed.email_id IS NOT NULL) AS email_duplicate_records
              FROM public.iti i
              LEFT JOIN admissions.iti_admissions a
                ON i.iti_code = a.iti_code
                """ + (yearFilter != null ? " AND a.year_of_admission = ? " : "") + """
              LEFT JOIN phone_duplicates pd ON a.iti_code = pd.iti_code AND a.phno = pd.phno
              LEFT JOIN aadhar_duplicates ad ON a.iti_code = ad.iti_code AND a.adarno = ad.adarno
              LEFT JOIN email_duplicates ed ON a.iti_code = ed.iti_code AND a.email_id = ed.email_id
              WHERE i.govt = """ + (govtFilter != null ? "?" : "i.govt") + """
              GROUP BY i.dist_code
            )
            SELECT d.dist_name, dd.dist_code,
                   COALESCE(dd.total, 0) AS total,
                   COALESCE(dd.success, 0) AS success,
                   COALESCE(dd.pending_sid, 0) AS pending_sid,
                   COALESCE(dd.verified, 0) AS verified,
                   COALESCE(dd.to_be_verified, 0) AS to_be_verified,
                   COALESCE(dd.to_be_updated, 0) AS to_be_updated,
                   COALESCE(dd.phone_duplicate_records, 0) AS phone_duplicate_records,
                   COALESCE(dd.aadhar_duplicate_records, 0) AS aadhar_duplicate_records,
                   COALESCE(dd.email_duplicate_records, 0) AS email_duplicate_records
            FROM public.dist_mst d
            LEFT JOIN district_data dd ON d.dist_code = dd.dist_code
            ORDER BY d.dist_name
            """;

        List<Object> params = new ArrayList<>();

        if (yearFilter != null) {
            params.add(yearFilter);
        }
        if (yearFilter != null) {
            params.add(yearFilter);
        }
        if (yearFilter != null) {
            params.add(yearFilter);
        }
        if (yearFilter != null) {
            params.add(yearFilter);
        }
        if (govtFilter != null) {
            params.add(govtFilter);
        }

        return jdbcTemplate.query(sql, (rs, rowNum) -> new StateDashboardResponse(
                rs.getString("dist_name"),
                rs.getString("dist_code"),
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

    // 10. Phase Wise Abstract
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

    // 11. Today Schedule ITIs
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

    // 12. Trade Wise Report
    @Override
    public List<TradeWiseReportResponse> getTradeWiseReport(String year, String distCode, String itiType) {
        StringBuilder sql = new StringBuilder("""
            WITH trade_seat_values AS (
                SELECT sm.trade_code, (svals(sm.strength))::int AS seat_value
                FROM public.iti_seatmatrix sm
                JOIN public.iti i ON sm.iti_code = i.iti_code
                JOIN public.ititrade it ON sm.iti_code = it.iti_code AND sm.trade_code::text = it.trade_code::text
                WHERE sm.year::text = ?::text
            ),
            trade_strength AS (
                SELECT trade_code::text, SUM(seat_value) AS total_strength
                FROM trade_seat_values
                GROUP BY trade_code
            )
            SELECT tm.trade_name, tm.trade_code::text AS trade_code,
                   COUNT(a.adm_num) AS filled,
                   COALESCE(ts.total_strength, 0) AS total_strength
            FROM public.ititrade_master tm
            LEFT JOIN trade_strength ts ON tm.trade_code::text = ts.trade_code::text
            JOIN public.ititrade it ON it.trade_code::text = tm.trade_code::text
            JOIN public.iti i ON it.iti_code = i.iti_code
            LEFT JOIN admissions.iti_admissions a ON a.iti_code = i.iti_code
                AND a.trade_code::text = tm.trade_code::text
                AND a.year_of_admission::text = ?::text
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND TRIM(i.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }
        if (itiType != null && !"All".equalsIgnoreCase(itiType)) {
            sql.append(" AND i.govt = ?");
            params.add(itiType);
        }

        sql.append(" GROUP BY tm.trade_name, tm.trade_code, ts.total_strength ORDER BY tm.trade_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            int filled = rs.getInt("filled");
            int total = rs.getInt("total_strength");
            int vacant = Math.max(0, total - filled);
            return new TradeWiseReportResponse(
                rs.getString("trade_name"),
                rs.getString("trade_code"),
                total,
                filled,
                vacant
            );
        }, params.toArray());
    }

    // 13. Applicant Report by Phase (State Wise - same as #2)
    // Note: Uses same implementation as #2

    // 14. Open Seats Abstract (District wise)
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

    // 15. Trade Duration Seats Abstract
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

    // 16. Govt/Pvt District Wise Seats Abstract
    @Override
    public List<GovtPvtSeatsAbstractResponse> getGovtPvtSeatsAbstract(String year, String govt) {
        boolean isGovtOnly = "G".equalsIgnoreCase(govt);
        boolean isPvtOnly = "P".equalsIgnoreCase(govt);

        String sql = """
            WITH govt_individual_seats AS (
                SELECT i.dist_code, (svals(sm.strength))::int AS strength_val
                FROM public.iti_seatmatrix sm
                JOIN public.iti i ON sm.iti_code = i.iti_code
                WHERE sm.year::text = ?::text AND i.govt = 'G'
            ),
            govt_seat_capacity AS (
                SELECT dist_code, SUM(strength_val) AS govt_strength
                FROM govt_individual_seats GROUP BY dist_code
            ),
            govt_filled_seats AS (
                SELECT i.dist_code, COUNT(*) AS govt_fill
                FROM admissions.iti_admissions a
                JOIN public.iti i ON a.iti_code = i.iti_code
                WHERE a.year_of_admission::text = ?::text AND i.govt = 'G'
                GROUP BY i.dist_code
            ),
            pvt_individual_seats AS (
                SELECT i.dist_code, (svals(sm.strength))::int AS strength_val
                FROM public.iti_seatmatrix sm
                JOIN public.iti i ON sm.iti_code = i.iti_code
                WHERE sm.year::text = ?::text AND i.govt = 'P'
            ),
            pvt_seat_capacity AS (
                SELECT dist_code, SUM(strength_val) AS pvt_strength
                FROM pvt_individual_seats GROUP BY dist_code
            ),
            pvt_filled_seats AS (
                SELECT i.dist_code, COUNT(*) AS pvt_fill
                FROM admissions.iti_admissions a
                JOIN public.iti i ON a.iti_code = i.iti_code
                WHERE a.year_of_admission::text = ?::text AND i.govt = 'P'
                GROUP BY i.dist_code
            )
            SELECT d.dist_code, d.dist_name,
                   COALESCE(gsc.govt_strength, 0) AS govt_strength, COALESCE(gf.govt_fill, 0) AS govt_fill,
                   COALESCE(psc.pvt_strength, 0) AS pvt_strength, COALESCE(pf.pvt_fill, 0) AS pvt_fill
            FROM (SELECT DISTINCT dist_code, dist_name FROM public.dist_mst) d
            LEFT JOIN govt_seat_capacity gsc ON d.dist_code = gsc.dist_code
            LEFT JOIN govt_filled_seats gf ON d.dist_code = gf.dist_code
            LEFT JOIN pvt_seat_capacity psc ON d.dist_code = psc.dist_code
            LEFT JOIN pvt_filled_seats pf ON d.dist_code = pf.dist_code
            WHERE (?::text = 'All' OR gsc.govt_strength IS NOT NULL OR psc.pvt_strength IS NOT NULL)
            ORDER BY d.dist_name
            """;

        List<GovtPvtSeatsAbstractResponse> results = jdbcTemplate.query(sql, (rs, rowNum) -> {
            int gs = rs.getInt("govt_strength");
            int gf = rs.getInt("govt_fill");
            int ps = rs.getInt("pvt_strength");
            int pf = rs.getInt("pvt_fill");

            if (isGovtOnly) {
                ps = 0; pf = 0;
            } else if (isPvtOnly) {
                gs = 0; gf = 0;
            }

            return new GovtPvtSeatsAbstractResponse(
                rs.getString("dist_code"), rs.getString("dist_name"),
                gs, gf, Math.max(0, gs - gf),
                ps, pf, Math.max(0, ps - pf),
                gs + ps, gf + pf,
                Math.max(0, (gs + ps) - (gf + pf))
            );
        }, year, year, year, year, govt != null ? govt : "All");

        return results;
    }

    // 17. Student Complete Details
    @Override
    public StudentCompleteDetailsResponse getStudentCompleteDetails(String regid, String admNum) {
        StudentCompleteDetailsResponse response = new StudentCompleteDetailsResponse();

        if (admNum != null && !admNum.isEmpty()) {
            try {
                String findSql = "SELECT regid FROM admissions.iti_admissions WHERE adm_num = ? LIMIT 1";
                regid = jdbcTemplate.queryForObject(findSql, String.class, admNum);
            } catch (Exception e) {
                try {
                    String findSql2025 = "SELECT regid FROM admissions.iti_admissions2025 WHERE adm_num = ? LIMIT 1";
                    regid = jdbcTemplate.queryForObject(findSql2025, String.class, admNum);
                } catch (Exception e2) {
                    return response;
                }
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
            System.err.println("[ReportServiceImpl] Unexpected error: " + e.getMessage());
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
                // Fallback to year-specific application table
                String appTable = "public.application" + detectedYear;
                String fallbackSql = "SELECT regid::text AS registration_id, name, fname AS father_name, addr AS address, " +
                        "mname AS mother_name, phno::text AS phone_no, gender, caste, ssc_passed, phc, " +
                        "dob::text AS date_of_birth, ssc_regno AS ssc_ht_no, ssc_year AS ssc_pass_year, " +
                        "phase::text AS registered_phase, entry_date::text AS registration_date, " +
                        "verified_date::text AS verified_date " +
                        "FROM " + appTable + " WHERE regid::text = ?::text LIMIT 1";
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
                } else {
                    // Fallback to iti_admissions
                    String admFallbackSql = """
                        SELECT regid::text AS registration_id, name, fname AS father_name, addr AS address,
                               mname AS mother_name, phno::text AS phone_no, gender, caste, ssc_passed, phc,
                               dob::text AS date_of_birth, ssc_regno AS ssc_ht_no, ssc_year AS ssc_pass_year,
                               phase::text AS registered_phase, date_of_admission::text AS registration_date,
                               NULL AS verified_date
                        FROM admissions.iti_admissions WHERE regid::text = ?::text LIMIT 1
                        """;
                    List<Map<String, Object>> admFallbackRows = jdbcTemplate.queryForList(admFallbackSql, finalRegid);
                    if (admFallbackRows.isEmpty()) {
                        String admFallbackSql2025 = """
                            SELECT regid::text AS registration_id, name, fname AS father_name, addr AS address,
                                   mname AS mother_name, phno::text AS phone_no, gender, caste, ssc_passed, phc,
                                   dob::text AS date_of_birth, ssc_regno AS ssc_ht_no, ssc_year AS ssc_pass_year,
                                   phase::text AS registered_phase, date_of_admission::text AS registration_date,
                                   NULL AS verified_date
                            FROM admissions.iti_admissions2025 WHERE regid::text = ?::text LIMIT 1
                            """;
                        admFallbackRows = jdbcTemplate.queryForList(admFallbackSql2025, finalRegid);
                    }
                    if (!admFallbackRows.isEmpty()) {
                        Map<String, Object> r = admFallbackRows.get(0);
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
            }
        } catch (Exception e) {
            System.err.println("[ReportServiceImpl] Unexpected error: " + e.getMessage());
        }

        // 2. SSC Marks
        String marksTable = (detectedYear == 2023) ? "public.cand_marks2023" : "public.cand_marks" + detectedYear;
        String marksSql = "SELECT ssc_first_lang_marks AS first_language, ssc_second_lang_marks AS second_language, " +
                "ssc_eng_marks AS english, ssc_math_marks AS maths, ssc_sci_marks AS science, " +
                "ssc_social_marks AS social, ssc_tot_marks AS total " +
                "FROM " + marksTable + " WHERE regid::text = ?::text";
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
            System.err.println("[ReportServiceImpl] Unexpected error: " + e.getMessage());
        }

        // 3. Applied ITIs
        List<AppliedIti> appliedList = new ArrayList<>();
        String[] checklistTables = {
                "public.checklist" + detectedYear + "phase1",
                "public.checklist" + detectedYear + "phase2",
                "public.checklist" + detectedYear + "phase3",
                "public.checklist" + detectedYear + "phase4",
                "public.checklist" + detectedYear + "phase5"
        };
        for (String checklistTable : checklistTables) {
            String appliedSql = "SELECT c.iti_code, i.iti_name, c.phase, sa.year AS admissions_year " +
                    "FROM " + checklistTable + " c " +
                    "LEFT JOIN public.iti i ON c.iti_code = i.iti_code " +
                    "LEFT JOIN public.application" + detectedYear + " sa ON c.regid = sa.regid " +
                    "WHERE c.regid = ? " +
                    "ORDER BY c.phase, i.iti_name";
            try {
                List<Map<String, Object>> appliedRows = jdbcTemplate.queryForList(appliedSql, Integer.parseInt(finalRegid));
                for (Map<String, Object> ap : appliedRows) {
                    appliedList.add(new AppliedIti(
                            str(ap.get("iti_code")), str(ap.get("iti_name")),
                            str(ap.get("phase")), str(ap.get("admissions_year"))
                    ));
                }
            } catch (Exception e) {
            System.err.println("[ReportServiceImpl] Unexpected error: " + e.getMessage());
        }
        }
        response.setAppliedItis(appliedList);

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
        List<MeritListDetail> meritList = new ArrayList<>();
        String[] ranksTables = {
                "public.ranks" + detectedYear + "phase1",
                "public.ranks" + detectedYear + "phase2",
                "public.ranks" + detectedYear + "phase3",
                "public.ranks" + detectedYear + "phase4",
                "public.ranks" + detectedYear + "phase5"
        };
        for (String ranksTable : ranksTables) {
            String meritSql = "SELECT d.dist_name, i.iti_name, r.rank, r.phase, r.qual AS qualification " +
                    "FROM " + ranksTable + " r " +
                    "LEFT JOIN public.dist_mst d ON r.dist_code = d.dist_code " +
                    "LEFT JOIN public.iti i ON r.iti_code = i.iti_code " +
                    "WHERE r.regid = ? " +
                    "ORDER BY r.phase";
            try {
                List<Map<String, Object>> meritRows = jdbcTemplate.queryForList(meritSql, Integer.parseInt(finalRegid));
                for (Map<String, Object> m : meritRows) {
                    meritList.add(new MeritListDetail(
                            str(m.get("dist_name")), str(m.get("iti_name")),
                            str(m.get("rank")), str(m.get("phase")), str(m.get("qualification"))
                    ));
                }
            } catch (Exception e) {
            System.err.println("[ReportServiceImpl] Unexpected error: " + e.getMessage());
        }
        }
        response.setMeritList(meritList);

        // 6. Admission details
        String[] admTables = {"admissions.iti_admissions", "admissions.iti_admissions" + detectedYear};
        for (String admTable : admTables) {
            String admSql = "SELECT d.dist_name AS district, i.iti_name AS iti, tm.trade_name AS trade, " +
                    "a.adm_num AS admission_number, a.res_category AS reservation_category, " +
                    "a.year_of_admission, a.phase::text, a.date_of_admission::text, a.phno::text AS phone_number " +
                    "FROM " + admTable + " a " +
                    "LEFT JOIN public.dist_mst d ON a.dist_code = d.dist_code " +
                    "LEFT JOIN public.iti i ON a.iti_code = i.iti_code " +
                    "LEFT JOIN public.ititrade_master tm ON a.trade_code = tm.trade_code " +
                    "WHERE a.regid::text = ?::text";
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
                    break;
                }
            } catch (Exception e) {
            System.err.println("[ReportServiceImpl] Unexpected error: " + e.getMessage());
        }
        }

        return response;
    }

    // 18. District Wise Application Count
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

    // 19. District Schedule
    @Override
    public List<DistrictScheduleResponse> getDistrictSchedule(String distCode, String year, int page, int size) {
        StringBuilder sql = new StringBuilder("""
            SELECT d.dist_name, i.iti_name, tm.trade_name,
                   a.merit_from, a.merit_to, a.cal_date, a.cal_time, a.phase
            FROM public.admission_timings a
            JOIN public.iti i ON a.iti_code = i.iti_code
            JOIN public.dist_mst d ON i.dist_code = d.dist_code
            LEFT JOIN public.ititrade_master tm ON a.minqul = tm.trade_code::text
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();

        if (year != null && !year.isEmpty()) {
            sql.append(" AND a.year::text = ?::text");
            params.add(year);
        }

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND TRIM(i.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }
        
        sql.append(" ORDER BY i.iti_name, a.phase LIMIT ? OFFSET ?");
        params.add(size);
        params.add((long) page * size);

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new DistrictScheduleResponse(
                rs.getString("dist_name"),
                rs.getString("iti_name"),
                rs.getString("trade_name"),
                rs.getInt("merit_from"),
                rs.getInt("merit_to"),
                rs.getString("cal_date"),
                rs.getString("cal_time"),
                rs.getString("phase")
        ), params.toArray());
    }

    // 20. Permitted Shift Unit Report
    @Override
    public List<ShiftUnitResponse> getPermittedShiftUnit(String distCode, String itiCode, int page, int size) {
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

        sql.append(" ORDER BY i.iti_name, tm.trade_name LIMIT ? OFFSET ?");
        params.add(size);
        params.add((long) page * size);

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ShiftUnitResponse(
                rs.getString("iti_name"),
                rs.getString("iti_type"),
                rs.getString("trade_name"),
                rs.getInt("strength"),
                rs.getString("shift"),
                rs.getString("unit")
        ), params.toArray());
    }

    // 4a. DSC Options
    @Override
    public DscOptionsResponse getDscOptions(String distCode, String itiCode) {
        List<DscOptionsResponse.ItiOption> itiOptions = new ArrayList<>();
        List<DscOptionsResponse.TradeOption> tradeOptions = new ArrayList<>();

        StringBuilder itiSql = new StringBuilder("SELECT iti_code, iti_name FROM public.iti WHERE 1=1");
        List<Object> itiParams = new ArrayList<>();
        if (distCode != null && !distCode.isEmpty()) {
            itiSql.append(" AND TRIM(dist_code::text) = TRIM(?::text)");
            itiParams.add(distCode);
        }
        if (itiCode != null && !itiCode.isEmpty()) {
            itiSql.append(" AND TRIM(iti_code::text) = TRIM(?::text)");
            itiParams.add(itiCode);
        }
        itiSql.append(" ORDER BY iti_name");
        jdbcTemplate.query(itiSql.toString(), (rs, rowNum) -> {
            itiOptions.add(new DscOptionsResponse.ItiOption(rs.getString("iti_code"), rs.getString("iti_name")));
            return null;
        }, itiParams.toArray());

        StringBuilder tradeSql = new StringBuilder("SELECT trade_code, trade_name FROM public.ititrade_master WHERE 1=1");
        List<Object> tradeParams = new ArrayList<>();
        tradeSql.append(" ORDER BY trade_name");
        jdbcTemplate.query(tradeSql.toString(), (rs, rowNum) -> {
            tradeOptions.add(new DscOptionsResponse.TradeOption(rs.getString("trade_code"), rs.getString("trade_name")));
            return null;
        }, tradeParams.toArray());

        return new DscOptionsResponse(itiOptions, tradeOptions);
    }

    // 21. ITI Admissions Report
    @Override
    public List<ITIAdmissionsReportResponse> getITIAdmissionsReport(String year, String distCode, String govt,
            String caste, String gender, String ncvtScvt, int page, int size) {
        StringBuilder sql = new StringBuilder("""
            SELECT DISTINCT a.adm_num AS admission_number, a.name, a.ssc_regno, a.year_of_admission
            FROM admissions.iti_admissions a
            LEFT JOIN public.iti i ON TRIM(a.iti_code::text) = TRIM(i.iti_code::text)
            WHERE a.year_of_admission::text = ?::text
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode)) {
            sql.append(" AND TRIM(a.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }
        if (govt != null && !"All".equalsIgnoreCase(govt)) {
            sql.append(" AND i.govt = ?");
            params.add("Govt".equalsIgnoreCase(govt) ? "G" : "P");
        }
        if (caste != null && !"All".equalsIgnoreCase(caste)) {
            sql.append(" AND TRIM(a.res_category) = ?");
            params.add(caste);
        }
        if (gender != null && !"All".equalsIgnoreCase(gender)) {
            String genderVal = gender.toUpperCase().startsWith("M") ? "M%" : "F%";
            sql.append(" AND a.gender ILIKE ?");
            params.add(genderVal);
        }
        if (ncvtScvt != null && !"All".equalsIgnoreCase(ncvtScvt)) {
            String typeVal = ncvtScvt.toUpperCase().startsWith("N") ? "N" : "S";
            sql.append(" AND TRIM(a.type_admission) = ?");
            params.add(typeVal);
        }

        sql.append(" ORDER BY a.name LIMIT ? OFFSET ?");
        params.add(size);
        params.add((long) page * size);

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new ITIAdmissionsReportResponse(
                rs.getString("admission_number"),
                rs.getString("name"),
                rs.getString("ssc_regno"),
                rs.getString("year_of_admission")
        ), params.toArray());
    }

    // 22. All Resource Role
    @Override
    public List<AllResourceRoleResponse> getAllResourceRoles(int page, int size) {
        String sql = """
            SELECT 
                   rm.rolename, 
                   lu.username, 
                   d.dist_name, 
                   i.iti_name,
                   i.mobile, 
                   i.email
            FROM public.login_users lu
            LEFT JOIN public.role_mast rm ON lu.roleid = rm.role_id
            LEFT JOIN public.iti i ON lu.ins_code = i.iti_code
            LEFT JOIN public.dist_mst d ON i.dist_code = d.dist_code
            ORDER BY rm.rolename, lu.username
            LIMIT ? OFFSET ?
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new AllResourceRoleResponse(
                rs.getString("rolename"),
                rs.getString("username"),
                rs.getString("dist_name"),
                rs.getString("iti_name"),
                rs.getString("mobile"),
                rs.getString("email")
        ), size, (long) page * size);
    }

    // 23. DistWise Strength+Filled Seats Abstract
    @Override
    public List<StrengthFilledSeatsResponse> getStrengthFilledSeatsAbstract(String year, String distCode) {
        StringBuilder sql = new StringBuilder("""
            WITH iti_seat_values AS (
                SELECT i.iti_code, i.iti_name, i.dist_code,
                       (svals(sm.strength))::int AS strength_val
                FROM public.iti i
                JOIN public.iti_seatmatrix sm ON i.iti_code = sm.iti_code
                    AND sm.year::text = ?::text
                WHERE 1=1
            ),
            iti_strength AS (
                SELECT iti_code, iti_name, dist_code,
                       COALESCE(SUM(strength_val), 0) AS strength
                FROM iti_seat_values
                GROUP BY iti_code, iti_name, dist_code
            ),
            iti_fill AS (
                SELECT iti_code, COUNT(*) AS strength_fill
                FROM admissions.iti_admissions
                WHERE year_of_admission::text = ?::text
                GROUP BY iti_code
            )
            SELECT s.iti_code, s.iti_name, d.dist_name,
                   s.strength,
                   COALESCE(f.strength_fill, 0) AS strength_fill
            FROM iti_strength s
            LEFT JOIN iti_fill f ON s.iti_code = f.iti_code
            JOIN public.dist_mst d ON s.dist_code = d.dist_code
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode) && !distCode.isEmpty()) {
            sql.append(" AND TRIM(s.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }

        sql.append(" ORDER BY d.dist_name, s.iti_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            int strength = rs.getInt("strength");
            int fill = rs.getInt("strength_fill");
            double pct = strength > 0 ? Math.round(((double) fill / strength) * 10000.0) / 100.0 : 0.0;
            return new StrengthFilledSeatsResponse(
                rs.getString("iti_code"),
                rs.getString("iti_name"),
                rs.getString("dist_name"),
                strength, fill, Math.max(0, strength - fill), pct
            );
        }, params.toArray());
    }

    // 24. Admission Report (Trade wise - same as #3)
    // Note: Uses same implementation as #3

    // 25. TradeWise Vacant Position
    @Override
    public List<TradeWiseVacantResponse> getTradeWiseVacantPositions(String year, String distCode) {
        StringBuilder sql = new StringBuilder("""
            WITH trade_seat_values AS (
                SELECT sm.trade_code, (svals(sm.strength))::int AS seat_value
                FROM public.iti_seatmatrix sm
                JOIN public.iti i ON sm.iti_code = i.iti_code
                JOIN public.ititrade it ON sm.iti_code = it.iti_code AND sm.trade_code::text = it.trade_code::text
                WHERE sm.year::text = ?::text
            ),
            trade_strength AS (
                SELECT trade_code::text, SUM(seat_value) AS total_strength
                FROM trade_seat_values
                GROUP BY trade_code
            )
            SELECT tm.trade_name, tm.trade_code::text AS trade_code,
                   COALESCE(ts.total_strength, 0) AS total_strength,
                   COUNT(a.adm_num) AS total_filled
            FROM public.ititrade_master tm
            LEFT JOIN trade_strength ts ON tm.trade_code::text = ts.trade_code::text
            JOIN public.ititrade it ON it.trade_code::text = tm.trade_code::text
            JOIN public.iti i ON it.iti_code = i.iti_code
            LEFT JOIN admissions.iti_admissions a ON a.iti_code = i.iti_code
                AND a.trade_code::text = tm.trade_code::text
                AND a.year_of_admission::text = ?::text
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        params.add(year);
        params.add(year);

        if (distCode != null && !"All".equalsIgnoreCase(distCode) && !distCode.isEmpty()) {
            sql.append(" AND TRIM(i.dist_code::text) = TRIM(?::text)");
            params.add(distCode);
        }

        sql.append(" GROUP BY tm.trade_name, tm.trade_code, ts.total_strength ORDER BY tm.trade_name");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            int strength = rs.getInt("total_strength");
            int filled = rs.getInt("total_filled");
            return new TradeWiseVacantResponse(
                rs.getString("trade_code"),
                rs.getString("trade_name"),
                strength, filled, Math.max(0, strength - filled)
            );
        }, params.toArray());
    }

    @Override
    public CurrentAdmissionPhaseResponse getCurrentAdmissionPhase() {
        String sql = "SELECT year, phase FROM admissions.admission_phase WHERE current = true LIMIT 1";
        List<CurrentAdmissionPhaseResponse> results = jdbcTemplate.query(sql, (rs, rowNum) ->
            new CurrentAdmissionPhaseResponse(rs.getString("year"), rs.getInt("phase"))
        );
        return results.isEmpty() ? null : results.get(0);
    }

    // 27. Students Not Admitted
    // Registered students who have no admission record in
    // admissions.iti_admissions for the given year. Optional phase filter.
    // Reads public.student_application first; if it has no rows for the year,
    // falls back to the year-partitioned table public.student_application<year>
    // (e.g. student_application2025), whose phase column is an hstore.
    private static final String NOT_ADMITTED_SELECT = """
            SELECT s.regid, s.name, s.fname, s.gender, s.caste, s.sub_caste,
                   s.dob, s.phno, s.adarno, s.email, s.year, %s,
                   s.app_status, s.entry_date, s.verified_date
            FROM %s s
            WHERE s.year = ?
              AND NOT EXISTS (
                  SELECT 1
                  FROM admissions.iti_admissions a
                  WHERE a.regid = s.regid::text
              )
            """;
    private static final String NOT_ADMITTED_COUNT = """
            SELECT COUNT(*)
            FROM %s s
            WHERE s.year = ?
              AND NOT EXISTS (
                  SELECT 1
                  FROM admissions.iti_admissions a
                  WHERE a.regid = s.regid::text
              )
            """;
    private static final String PHASE_PLAIN = "s.phase";
    private static final String PHASE_HSTORE =
            "COALESCE((SELECT k FROM unnest(akeys(s.phase)) k WHERE k <> '' LIMIT 1), '') AS phase";

    // Returns the table to read: base table when it has rows for the year,
    // otherwise the year-partitioned table if it exists. Year must be digits
    // (validated) since the table name is interpolated, not bound.
    private String resolveNotAdmittedTable(String year) {
        String base = "public.student_application";
        if (year == null || !year.matches("\\d{1,4}")) return base;
        Long cnt = jdbcTemplate.query(
                "SELECT COUNT(*) FROM public.student_application WHERE year = ?",
                rs -> rs.next() ? rs.getLong(1) : 0L, year);
        if (cnt != null && cnt > 0) return base;
        Object reg = jdbcTemplate.queryForObject(
                "SELECT to_regclass(?)", Object.class, "public.student_application" + year);
        return reg != null ? "public.student_application" + year : base;
    }

    private boolean isFallbackTable(String table) {
        return !table.equals("public.student_application");
    }

    private void appendPhaseFilter(StringBuilder sql, List<Object> params, Integer phase, boolean hstorePhase) {
        if (phase != null && phase > 0) {
            if (hstorePhase) {
                sql.append(" AND exist(s.phase, ?)");
            } else {
                sql.append(" AND s.phase = ?");
            }
            params.add(String.valueOf(phase));
        }
    }

    @Override
    public List<String> getStudentsNotAdmittedYears() {
        // Years available: distinct years in the base table plus any
        // year-partitioned tables (student_application<year>) that exist.
        String sql = """
            SELECT DISTINCT yr FROM (
                SELECT year AS yr FROM public.student_application WHERE year IS NOT NULL AND year <> ''
                UNION
                SELECT regexp_replace(table_name, '^student_application', '')
                FROM information_schema.tables
                WHERE table_schema = 'public'
                  AND table_name ~ '^student_application[0-9]{4}$'
            ) t
            WHERE yr ~ '^[0-9]{4}$'
            ORDER BY yr DESC
            """;
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<NotAdmittedStudentResponse> getStudentsNotAdmitted(String year, Integer phase, int page, int size) {
        String table = resolveNotAdmittedTable(year);
        boolean hstorePhase = isFallbackTable(table);
        StringBuilder sql = new StringBuilder(String.format(NOT_ADMITTED_SELECT,
                hstorePhase ? PHASE_HSTORE : PHASE_PLAIN, table));
        List<Object> params = new ArrayList<>();
        params.add(year);
        appendPhaseFilter(sql, params, phase, hstorePhase);
        sql.append(" ORDER BY s.regid LIMIT ? OFFSET ?");
        params.add(size);
        params.add((long) page * size);

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> new NotAdmittedStudentResponse(
                rs.getLong("regid"),
                rs.getString("name"),
                rs.getString("fname"),
                rs.getString("gender"),
                rs.getString("caste"),
                rs.getString("sub_caste"),
                str(rs.getDate("dob")),
                rs.getObject("phno") != null ? rs.getLong("phno") : null,
                rs.getString("adarno"),
                rs.getString("email"),
                rs.getString("year"),
                rs.getString("phase"),
                rs.getString("app_status"),
                rs.getTimestamp("entry_date") != null ? rs.getTimestamp("entry_date").toLocalDateTime() : null,
                rs.getTimestamp("verified_date") != null ? rs.getTimestamp("verified_date").toLocalDateTime() : null
        ), params.toArray());
    }

    @Override
    public long countStudentsNotAdmitted(String year, Integer phase) {
        String table = resolveNotAdmittedTable(year);
        boolean hstorePhase = isFallbackTable(table);
        StringBuilder sql = new StringBuilder(String.format(NOT_ADMITTED_COUNT, table));
        List<Object> params = new ArrayList<>();
        params.add(year);
        appendPhaseFilter(sql, params, phase, hstorePhase);
        Long count = jdbcTemplate.query(sql.toString(), rs -> rs.next() ? rs.getLong(1) : 0L, params.toArray());
        return count != null ? count : 0;
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