package com.server.backend.service.Placements;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.Placements.PlacementsDistinctItiResponse;
import com.server.backend.DTO.Placements.PlacementsGroupedResponse;
import com.server.backend.DTO.Placements.PlacementsOverviewResponse;

@Service
public class PlacementsServiceImpl implements PlacementsService {

    private final JdbcTemplate jdbcTemplate;

    public PlacementsServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PlacementsOverviewResponse getOverviewDetails() {
        long placementsCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM placements.placements", Long.class);

        PlacementsOverviewResponse response = new PlacementsOverviewResponse();
        response.setAllPlacement(placementsCount);
        return response;
    }

    @Override
    public PlacementsGroupedResponse getCountPlacementsGroupedByPtype() {
        String sql = "SELECT ptype, COUNT(*) FROM placements.placements GROUP BY ptype ORDER BY ptype";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        PlacementsGroupedResponse response = new PlacementsGroupedResponse();
        for (Map<String, Object> row : rows) {
            String ptype = (String) row.get("ptype");
            Long count = (Long) row.get("count");
            if (ptype == null || count == null) continue;
            switch (ptype.trim().toLowerCase()) {
                case "job" -> response.setJob(count);
                case "oj" -> response.setOA(count);
                case "apprenticeship" -> response.setApprenticeship(count);
                case "oa" -> response.setOA(count);
                case "highereducation" -> response.setHigherEducation(count);
                case "selfemployment" -> response.setSelfEmployment(count);
            }
        }
        return response;
    }

    @Override
    public PlacementsDistinctItiResponse getDistinctItiCodesByPtype() {
        String sql = "SELECT ptype, COUNT(DISTINCT iti_code) as iti_count FROM placements.placements GROUP BY ptype ORDER BY ptype";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        PlacementsDistinctItiResponse response = new PlacementsDistinctItiResponse();
        for (Map<String, Object> row : rows) {
            String ptype = (String) row.get("ptype");
            Long count = ((Number) row.get("iti_count")).longValue();
            if (ptype == null) continue;
            switch (ptype.trim().toLowerCase()) {
                case "job" -> response.setJobItisCount(count);
                case "oj" -> response.setOjItisCount(count);
                case "apprenticeship" -> response.setApprenticeshipItisCount(count);
                case "oa" -> response.setOaItisCount(count);
                case "highereducation" -> response.setHigherEducationItisCount(count);
                case "selfemployment" -> response.setSelfEmploymentItisCount(count);
            }
        }
        return response;
    }

    @Override
    public List<Map<String, Object>> getSchedulewiseDistrictData(String year) {
        String sql = "SELECT s.dist_code AS \"distCode\", COALESCE(d.dist_name, s.dist_code) AS \"distName\", "
                   + "COUNT(*) FILTER (WHERE s.schedule_type ILIKE '%job%') AS \"jobSchedules\", "
                   + "COUNT(*) FILTER (WHERE s.schedule_type ILIKE '%appre%') AS \"apprenticeshipSchedules\", "
                   + "COUNT(*) AS \"totalSchedules\" "
                   + "FROM placements.placements_schedules s "
                   + "LEFT JOIN public2.dist_mst d ON d.dist_code = s.dist_code "
                   + "WHERE s.schedule_date LIKE ? "
                   + "GROUP BY s.dist_code, COALESCE(d.dist_name, s.dist_code) "
                   + "ORDER BY \"distName\"";
        return jdbcTemplate.queryForList(sql, year + '%');
    }

    @Override
    public List<Map<String, Object>> getSchedulewiseItiData(String year, String distCode) {
        String sql = "SELECT s.plcmt_id AS id, COALESCE(i.iti_name, s.schedule_location) AS itiName, "
                   + "s.schedule_type AS type, s.schedule_date AS date, s.schedule_desc AS description, "
                   + "s.no_of_vacancies AS vacancies, s.no_of_attended_candidates AS attended, "
                   + "s.no_of_selected_candidates AS selected, "
                   + "(SELECT COUNT(*) FROM placements.placements p WHERE p.plcmt_id = s.plcmt_id) AS recordsCount "
                   + "FROM placements.placements_schedules s "
                   + "LEFT JOIN public2.iti i ON i.iti_code = s.schedule_location "
                   + "WHERE s.schedule_date LIKE ? AND s.dist_code = ? "
                   + "ORDER BY s.plcmt_id";
        return jdbcTemplate.queryForList(sql, year + '%', distCode);
    }

    @Override
    public List<Map<String, Object>> getSchedulewisePlacementDetails(String plcmtId) {
        String sql = "SELECT p.name, p.adm_num AS admNum, p.iti_name AS itiName, "
                   + "p.dist_name AS distName, p.ptype, p.pname_of_company AS company, "
                   + "p.ppostname AS post, p.psalary AS salary, p.ptrade AS trade, "
                   + "p.schedule_id AS scheduleId, p.plcmt_year AS yr "
                   + "FROM placements.placements p WHERE p.plcmt_id = ? ORDER BY p.name";
        return jdbcTemplate.queryForList(sql, Long.valueOf(plcmtId));
    }

    @Override
    public List<Map<String, Object>> getDatewiseScheduleData(String fromDate, String toDate, String ptype) {
        StringBuilder sql = new StringBuilder(
            "SELECT s.plcmt_id AS \"scheduleId\", s.schedule_date AS \"date\", "
          + "s.dist_code AS \"distCode\", COALESCE(d.dist_name, s.dist_code) AS \"distName\", "
          + "COALESCE(i.iti_name, s.schedule_location) AS \"itiName\", "
          + "s.schedule_type AS \"type\", s.schedule_desc AS \"description\", "
          + "s.no_of_vacancies AS \"vacancies\", s.no_of_attended_candidates AS \"attended\", "
          + "s.no_of_selected_candidates AS \"selected\", "
          + "(SELECT COUNT(*) FROM placements.placements p WHERE p.plcmt_id = s.plcmt_id) AS \"recordsCount\" "
          + "FROM placements.placements_schedules s "
          + "LEFT JOIN public2.dist_mst d ON d.dist_code = s.dist_code "
          + "LEFT JOIN public2.iti i ON i.iti_code = s.schedule_location "
          + "WHERE s.schedule_date BETWEEN ? AND ? ");
        List<Object> params = new java.util.ArrayList<>(java.util.List.of(fromDate, toDate));
        if (ptype != null && !ptype.isBlank()) {
            sql.append("AND s.schedule_type = ? ");
            params.add(ptype);
        }
        sql.append("ORDER BY s.schedule_date, s.plcmt_id");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    /** ptype column mapping: Job=CAMPUS PLACEMENT, OJ=DIRECT PLACEMENT, Apprenticeship=APPRENTICESHIP,
     *  OA=DIRECT APPRENTICESHIP, SelfEmployment=SELF EMPLOYMENT, HigherEducation=HIGHER EDUCATION */
    private static final String PTYPE_COUNTS =
          "COUNT(*) FILTER (WHERE UPPER(TRIM(p.ptype)) = 'JOB') AS \"campusPlacement\", "
        + "COUNT(*) FILTER (WHERE UPPER(TRIM(p.ptype)) = 'OJ') AS \"directPlacement\", "
        + "COUNT(*) FILTER (WHERE UPPER(TRIM(p.ptype)) = 'APPRENTICESHIP') AS \"apprenticeship\", "
        + "COUNT(*) FILTER (WHERE UPPER(TRIM(p.ptype)) = 'OA') AS \"directApprenticeship\", "
        + "COUNT(*) FILTER (WHERE UPPER(TRIM(p.ptype)) = 'SELFEMPLOYMENT') AS \"selfEmployment\", "
        + "COUNT(*) FILTER (WHERE UPPER(TRIM(p.ptype)) = 'HIGHEREDUCATION') AS \"higherEducation\", "
        + "COUNT(*) AS \"total\" ";

    @Override
    public List<Map<String, Object>> getStatePlacementReport() {
        String sql = "SELECT p.dist_code AS \"distCode\", COALESCE(d.dist_name, p.dist_code) AS \"distName\", "
                   + PTYPE_COUNTS
                   + "FROM placements.placements p "
                   + "LEFT JOIN public2.dist_mst d ON d.dist_code = p.dist_code "
                   + "GROUP BY p.dist_code, COALESCE(d.dist_name, p.dist_code) "
                   + "ORDER BY \"distName\"";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getDistrictPlacementReport(String distCode) {
        String sql = "SELECT COALESCE(NULLIF(TRIM(p.iti_name), ''), p.iti_code) AS \"itiName\", "
                   + "p.plcmt_year AS \"year\", "
                   + PTYPE_COUNTS
                   + "FROM placements.placements p "
                   + "WHERE p.dist_code = ? "
                   + "GROUP BY COALESCE(NULLIF(TRIM(p.iti_name), ''), p.iti_code), p.plcmt_year "
                   + "ORDER BY \"itiName\", \"year\" DESC";
        return jdbcTemplate.queryForList(sql, distCode);
    }

    @Override
    public List<Map<String, Object>> getYearwisePlacementReport() {
        String sql = "SELECT p.plcmt_year AS \"year\", "
                   + PTYPE_COUNTS
                   + "FROM placements.placements p "
                   + "WHERE p.plcmt_year IS NOT NULL AND p.plcmt_year <> '' "
                   + "GROUP BY p.plcmt_year "
                   + "ORDER BY \"year\" DESC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getYearwisePlacementDetails(String year) {
        String sql = "SELECT p.dist_code AS \"distCode\", COALESCE(d.dist_name, p.dist_code) AS \"distName\", "
                   + PTYPE_COUNTS
                   + "FROM placements.placements p "
                   + "LEFT JOIN public2.dist_mst d ON d.dist_code = p.dist_code "
                   + "WHERE p.plcmt_year = ? "
                   + "GROUP BY p.dist_code, COALESCE(d.dist_name, p.dist_code) "
                   + "ORDER BY \"distName\"";
        return jdbcTemplate.queryForList(sql, year);
    }

    @Override
    public List<String> getDistinctAdmissionYears() {
        String sql = "SELECT DISTINCT year_of_admission FROM admissions.iti_admissions "
                   + "WHERE year_of_admission IS NOT NULL ORDER BY year_of_admission DESC";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    /** State Skill Development Plan report: trade-wise seats, admissions (gender split) and placements
     *  for an admission year. Seats come from public.iti_seatmatrix (hstore strength),
     *  admissions from admissions.iti_admissions, placements from placements.placements (all years). */
    @Override
    public List<Map<String, Object>> getStateSkillDevelopmentPlanReport(String year) {
        String sql = "SELECT tm.trade_name AS \"tradeName\", "
                   + "COUNT(DISTINCT a.iti_code) AS \"itiCount\", "
                   + "COALESCE(sm.total_strength, 0) AS \"totalStrength\", "
                   + "COUNT(*) FILTER (WHERE UPPER(a.gender) LIKE 'M%') AS \"totalMale\", "
                   + "COUNT(*) FILTER (WHERE UPPER(a.gender) LIKE 'F%') AS \"totalFemale\", "
                   + "COUNT(*) AS \"totalGender\", "
                   + "COALESCE(pl.plcmts, 0) AS \"totalPlcmts\" "
                   + "FROM admissions.iti_admissions a "
                   + "JOIN public.ititrade_master tm ON a.trade_code = tm.trade_code "
                   + "LEFT JOIN (SELECT trade_code, SUM(seat_value) AS total_strength "
                   + "          FROM (SELECT trade_code, (svals(strength))::int AS seat_value "
                   + "                FROM public.iti_seatmatrix WHERE year = ?) sv "
                   + "          GROUP BY trade_code) sm "
                   + "          ON sm.trade_code = tm.trade_code "
                   + "LEFT JOIN (SELECT trade_code, COUNT(*) AS plcmts "
                   + "          FROM placements.placements GROUP BY trade_code) pl "
                   + "          ON pl.trade_code::text = tm.trade_code::text "
                   + "WHERE a.year_of_admission::text = ?::text "
                   + "GROUP BY tm.trade_name, sm.total_strength, pl.plcmts "
                   + "ORDER BY \"tradeName\"";
        return jdbcTemplate.queryForList(sql, year, year);
    }

    /** Placement Data Details (current year + seniors): per-ITI admitted (1yr/2yr trades via
     *  ititrade_master.durationyrs months) vs placements of the passed year by ptype group. */
    @Override
    public List<Map<String, Object>> getPlacementDataDetailsReport(String year, String itiType) {
        StringBuilder sql = new StringBuilder(
              "SELECT COALESCE(d.dist_name, i.dist_code) AS \"district\", "
            + "COALESCE(i.iti_name, a.iti_code) AS \"iti\", "
            + "a.iti_code AS \"misCode\", "
            + "COUNT(*) FILTER (WHERE tm.durationyrs = 12) AS \"admitted1Year\", "
            + "COUNT(*) FILTER (WHERE tm.durationyrs = 24) AS \"admitted2Year\", "
            + "COUNT(*) AS \"totalAppeared\", "
            + "COALESCE(pl.jobOJ, 0) AS \"campusDirect\", "
            + "COALESCE(pl.appOA, 0) AS \"apprenticeshipOA\", "
            + "COALESCE(pl.selfEmp, 0) AS \"selfEmployment\", "
            + "COALESCE(pl.higherEdu, 0) AS \"higherEducation\", "
            + "COALESCE(pl.totalPlcmts, 0) AS \"totalPlacements\" "
            + "FROM admissions.iti_admissions a "
            + "JOIN public.iti i ON i.iti_code = a.iti_code "
            + "LEFT JOIN public2.dist_mst d ON d.dist_code = i.dist_code "
            + "JOIN public.ititrade_master tm ON tm.trade_code = a.trade_code "
            + "LEFT JOIN (SELECT iti_code, "
            + "          COUNT(*) FILTER (WHERE UPPER(TRIM(ptype)) IN ('JOB', 'OJ')) AS jobOJ, "
            + "          COUNT(*) FILTER (WHERE UPPER(TRIM(ptype)) IN ('APPRENTICESHIP', 'OA')) AS appOA, "
            + "          COUNT(*) FILTER (WHERE UPPER(TRIM(ptype)) = 'SELFEMPLOYMENT') AS selfEmp, "
            + "          COUNT(*) FILTER (WHERE UPPER(TRIM(ptype)) = 'HIGHEREDUCATION') AS higherEdu, "
            + "          COUNT(*) AS totalPlcmts "
            + "          FROM placements.placements WHERE plcmt_year = ? GROUP BY iti_code) pl "
            + "          ON pl.iti_code = a.iti_code "
            + "WHERE a.year_of_admission::text = ?::text ");
        if ("G".equalsIgnoreCase(itiType) || "P".equalsIgnoreCase(itiType)) {
            sql.append("AND i.govt = ? ");
        }
        sql.append("GROUP BY COALESCE(d.dist_name, i.dist_code), COALESCE(i.iti_name, a.iti_code), a.iti_code, "
                 + "pl.jobOJ, pl.appOA, pl.selfEmp, pl.higherEdu, pl.totalPlcmts "
                 + "ORDER BY \"district\", \"iti\"");
        if ("G".equalsIgnoreCase(itiType) || "P".equalsIgnoreCase(itiType)) {
            return jdbcTemplate.queryForList(sql.toString(), year, year, itiType.toUpperCase());
        }
        return jdbcTemplate.queryForList(sql.toString(), year, year);
    }

    @Override
    public List<Map<String, Object>> getDistrictScheduleItis(String distCode) {
        String sql = "SELECT iti_code AS \"itiCode\", iti_name AS \"itiName\" "
                   + "FROM public.iti WHERE dist_code = ? AND iti_code IS NOT NULL "
                   + "ORDER BY iti_name";
        return jdbcTemplate.queryForList(sql, distCode);
    }

    @Override
    public List<Map<String, Object>> getDistrictSchedules(String distCode) {
        String sql = "SELECT s.plcmt_id AS \"id\", "
                   + "COALESCE(i.iti_name, s.schedule_location) AS \"location\", "
                   + "s.schedule_date AS \"date\", s.schedule_type AS \"type\", "
                   + "s.schedule_desc AS \"description\" "
                   + "FROM placements.placements_schedules s "
                   + "LEFT JOIN public.iti i ON i.iti_code = s.schedule_location "
                   + "WHERE s.dist_code = ? "
                   + "ORDER BY s.schedule_date DESC NULLS LAST, s.plcmt_id DESC";
        return jdbcTemplate.queryForList(sql, distCode);
    }

    @Override
    public Map<String, Object> createSchedule(Map<String, Object> req) {
        String distCode = (String) req.get("distCode");
        String scheduleType = (String) req.get("scheduleType");
        String scheduleDate = (String) req.get("scheduleDate");
        String scheduleLocation = (String) req.get("scheduleLocation");
        String scheduleDesc = (String) req.get("scheduleDesc");

        if (distCode == null || distCode.isBlank()) {
            return Map.of("status", "ERROR", "message", "Missing district code.");
        }
        if (scheduleType == null || scheduleLocation == null || scheduleDate == null
                || scheduleType.isBlank() || scheduleLocation.isBlank() || scheduleDate.isBlank()) {
            return Map.of("status", "ERROR", "message", "Schedule Type, Date and Location are required.");
        }
        if (!"Job".equalsIgnoreCase(scheduleType) && !"Apprenticeship".equalsIgnoreCase(scheduleType)) {
            return Map.of("status", "ERROR", "message", "Invalid schedule type.");
        }

        // Duplicate check: same district + date + location + type already exists
        Integer dup = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM placements.placements_schedules "
          + "WHERE dist_code = ? AND schedule_date = ? AND schedule_location = ? "
          + "AND UPPER(TRIM(schedule_type)) = ?",
            Integer.class, distCode, scheduleDate, scheduleLocation, scheduleType.toUpperCase().trim());
        if (dup != null && dup > 0) {
            return Map.of("status", "ERROR",
                "message", "Schedule already exists for this ITI, date and type.");
        }

        jdbcTemplate.update(
            "INSERT INTO placements.placements_schedules "
          + "(dist_code, schedule_date, schedule_type, schedule_location, schedule_desc, "
          + " no_of_vacancies, no_of_attended_candidates, no_of_selected_candidates, "
          + " entry_by, entry_date_time) "
          + "VALUES (?, ?, ?, ?, ?, NULL, NULL, NULL, ?, now())",
            distCode, scheduleDate, scheduleType, scheduleLocation, scheduleDesc, distCode);

        return Map.of("status", "SUCCESS", "message", "Schedule saved successfully.");
    }

    @Override
    public List<String> getDistrictPlacementYears(String distCode) {
        String sql = "SELECT DISTINCT plcmt_year FROM placements.placements "
                   + "WHERE dist_code = ? AND plcmt_year IS NOT NULL AND plcmt_year <> '' "
                   + "ORDER BY plcmt_year DESC";
        return jdbcTemplate.queryForList(sql, String.class, distCode);
    }

    @Override
    public List<Map<String, Object>> getDistrictItis(String distCode) {
        String sql = "SELECT iti_code AS \"itiCode\", iti_name AS \"itiName\" "
                   + "FROM public.iti WHERE dist_code = ? AND iti_code IS NOT NULL "
                   + "ORDER BY iti_name";
        return jdbcTemplate.queryForList(sql, distCode);
    }

    @Override
    public List<Map<String, Object>> getDistrictPlacementReport(String distCode, String ptype, String year, String itiCode) {
        StringBuilder sql = new StringBuilder(
              "SELECT p.iti_code AS \"itiCode\", COALESCE(i.iti_name, p.iti_code) AS \"itiName\", "
            + "COUNT(*) AS \"total\" "
            + "FROM placements.placements p "
            + "LEFT JOIN public.iti i ON i.iti_code = p.iti_code "
            + "WHERE p.dist_code = ? ");
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(distCode);
        if (ptype != null && !ptype.isBlank()) {
            sql.append("AND UPPER(TRIM(p.ptype)) = ? ");
            params.add(ptype.trim().toUpperCase());
        }
        if (year != null && !year.isBlank()) {
            sql.append("AND p.plcmt_year = ? ");
            params.add(year);
        }
        if (itiCode != null && !itiCode.isBlank()) {
            sql.append("AND p.iti_code = ? ");
            params.add(itiCode);
        }
        sql.append("GROUP BY p.iti_code, COALESCE(i.iti_name, p.iti_code) "
                 + "ORDER BY \"itiName\"");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    // ===== Placements Entry (ITI) =====
    @Override
    public Map<String, Object> getCandidateByAdmNum(String admNum, String itiCode) {
        String sql = "SELECT a.name, a.fname, a.adm_num AS admNum, a.iti_code AS itiCode, "
                   + "COALESCE(i.iti_name, a.iti_code) AS itiName, a.dist_code AS distCode, "
                   + "COALESCE(d.dist_name, a.dist_code) AS distName, a.trade_code AS tradeCode, "
                   + "COALESCE(tm.trade_name, CAST(a.trade_code AS text)) AS tradeName, "
                   + "a.year_of_admission AS admissionYear "
                   + "FROM admissions.iti_admissions a "
                   + "LEFT JOIN public.iti i ON i.iti_code = a.iti_code "
                   + "LEFT JOIN public.dist_mst d ON d.dist_code = a.dist_code "
                   + "LEFT JOIN public.ititrade_master tm ON tm.trade_code = a.trade_code "
                   + "WHERE a.adm_num = ? AND a.iti_code = ? "
                   + "ORDER BY a.year_of_admission DESC LIMIT 1";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, admNum, itiCode);
        return rows.isEmpty() ? java.util.Map.of("status", "NOT_FOUND") : rows.get(0);
    }

    @Override
    public List<Map<String, Object>> findCandidatesByName(String name, String itiCode) {
        String sql = "SELECT a.name, a.fname AS \"fatherName\", a.adm_num AS \"admNum\" "
                   + "FROM admissions.iti_admissions a "
                   + "WHERE a.iti_code = ? AND UPPER(a.name) LIKE ? "
                   + "ORDER BY a.name LIMIT 50";
        return jdbcTemplate.queryForList(sql, itiCode, "%" + name.trim().toUpperCase() + "%");
    }

    @Override
    public List<Map<String, Object>> getCandidatePlacements(String admNum, String itiCode) {
        String sql = "SELECT p.pid AS id, p.ptype, p.passyear AS passYear, p.passmonth AS passMonth, "
                   + "p.pname_of_company AS company, p.ppostname AS post, p.psalary AS salary, "
                   + "p.ptrade AS trade, p.pstipendamt AS stipendAmount, "
                   + "p.paaprstartdate AS apprStart, p.paaprenddate AS apprEnd, "
                   + "p.phrno AS hrContact, p.pcoursename AS courseName, p.pclgname AS collegeName, "
                   + "p.pselfemp AS selfEmployment, p.pmonthincome AS monthlyIncome, p.paddress AS address "
                   + "FROM placements.placements p "
                   + "WHERE p.adm_num = ? AND p.iti_code = ? "
                   + "ORDER BY p.entry_date DESC NULLS LAST";
        return jdbcTemplate.queryForList(sql, admNum, itiCode);
    }

    @Override
    public List<Map<String, Object>> getItiSchedules(String itiCode) {
        String sql = "SELECT s.plcmt_id AS id, s.schedule_date AS date, s.schedule_type AS type, "
                   + "s.schedule_desc AS description "
                   + "FROM placements.placements_schedules s "
                   + "WHERE s.schedule_location = ? "
                   + "ORDER BY s.schedule_date DESC NULLS LAST";
        return jdbcTemplate.queryForList(sql, itiCode);
    }

    @Override
    public List<Map<String, Object>> getMasterTrades() {
        String sql = "SELECT trade_code AS tradeCode, trade_name AS tradeName "
                   + "FROM public.ititrade_master ORDER BY trade_name";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getMasterStates() {
        String sql = "SELECT statecode AS stateCode, statename AS stateName "
                   + "FROM public.state_mst ORDER BY statename";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getMasterDistricts() {
        String sql = "SELECT dist_code AS code, dist_name AS name "
                   + "FROM public.dist_mst ORDER BY dist_name";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public Map<String, Object> createPlacement(Map<String, Object> req) {
        String admNum = (String) req.get("adm_num");
        String itiCode = (String) req.get("iti_code");
        String itiName = (String) req.get("iti_name");
        String distCode = (String) req.get("dist_code");
        String distName = (String) req.get("dist_name");
        String name = (String) req.get("name");
        String ptype = (String) req.get("ptype");
        String plcmtYear = (String) req.get("plcmtYear");
        String passmonth = (String) req.get("passmonth");
        String passyear = (String) req.get("passyear");
        String tradeCode = (String) req.get("trade_code");
        String tradeName = (String) req.get("trade_name");
        String yearOfAdmission = (String) req.get("year_of_admission");
        String scheduleId = (String) req.get("scheduleId");
        String entryBy = (String) req.get("entry_by");
        String entryDistCode = (String) req.get("entry_distcode");

        if (admNum == null || itiCode == null || ptype == null || plcmtYear == null
                || admNum.isBlank() || itiCode.isBlank() || ptype.isBlank() || plcmtYear.isBlank()) {
            return java.util.Map.of("status", "ERROR", "message", "Admission Number, Placement Type and Placement Year are required.");
        }
        boolean isJobLike = ptype.equalsIgnoreCase("Job") || ptype.equalsIgnoreCase("OJ");
        if (isJobLike) {
            if (isEmpty(req.get("pname_of_company"))) {
                return java.util.Map.of("status", "ERROR", "message", "Company Name is required.");
            }
        } else if (ptype.equalsIgnoreCase("Apprenticeship") || ptype.equalsIgnoreCase("OA")) {
            if (isEmpty(req.get("ptrade"))) {
                return java.util.Map.of("status", "ERROR", "message", "Apprenticeship Trade is required.");
            }
        } else if (ptype.equalsIgnoreCase("SelfEmployment")) {
            if (isEmpty(req.get("pselfemp"))) {
                return java.util.Map.of("status", "ERROR", "message", "Self Employment Name is required.");
            }
        } else if (ptype.equalsIgnoreCase("HigherEducation")) {
            if (isEmpty(req.get("pcoursename"))) {
                return java.util.Map.of("status", "ERROR", "message", "Course Name is required.");
            }
        }

        Integer scheduleDbId = null;
        if (scheduleId != null && !scheduleId.isBlank()) {
            try { scheduleDbId = Integer.valueOf(scheduleId); } catch (NumberFormatException ignored) { }
        }

        Integer pid = jdbcTemplate.queryForObject("SELECT nextval('placements.placements_id_seq')", Integer.class);

        jdbcTemplate.update(
            "INSERT INTO placements.placements "
          + "(pid, adm_num, dist_code, dist_name, entry_by, entry_distcode, iti_code, iti_name, name, "
          + " paaprenddate, paaprstartdate, paddress, passmonth, passyear, pclgname, pcoursename, "
          + " pdistrict, phrno, pmonthincome, pname_of_company, ppostname, psalary, pselfemp, "
          + " pstate, pstipendamt, ptrade, ptype, schedule_id, trade_code, trade_name, "
          + " year_of_admission, plcmt_year, entry_date, plcmt_id) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)",
            pid, admNum, distCode, distName, entryBy, entryDistCode, itiCode, itiName, name,
            (String) req.get("paaprenddate"), (String) req.get("paaprstartdate"), (String) req.get("paddress"),
            passmonth, passyear, (String) req.get("pclgname"), (String) req.get("pcoursename"),
            (String) req.get("pdistrict"), (String) req.get("phrno"), (String) req.get("pmonthincome"),
            (String) req.get("pname_of_company"), (String) req.get("ppostname"), (String) req.get("psalary"),
            (String) req.get("pselfemp"), (String) req.get("pstate"), (String) req.get("pstipendamt"),
            (String) req.get("ptrade"), ptype, scheduleDbId, tradeCode, tradeName,
            yearOfAdmission, plcmtYear, scheduleDbId);

        return java.util.Map.of("status", "SUCCESS", "message", "Placement saved successfully.");
    }

    @Override
    public List<String> getItiPlacementYears(String itiCode) {
        String sql = "SELECT DISTINCT plcmt_year FROM placements.placements "
                   + "WHERE iti_code = ? AND plcmt_year IS NOT NULL AND plcmt_year <> '' "
                   + "ORDER BY plcmt_year DESC";
        return jdbcTemplate.queryForList(sql, String.class, itiCode);
    }

    @Override
    public List<Map<String, Object>> getItiPlacementReport(String itiCode, String ptype, String year) {
        StringBuilder sql = new StringBuilder(
              "SELECT p.dist_code AS \"distCode\", COALESCE(d.dist_name, p.dist_code) AS \"distName\", "
            + "COUNT(*) AS \"total\" "
            + "FROM placements.placements p "
            + "LEFT JOIN public.dist_mst d ON d.dist_code = p.dist_code "
            + "WHERE p.iti_code = ? ");
        List<Object> params = new java.util.ArrayList<>();
        params.add(itiCode);
        if (ptype != null && !ptype.isBlank()) {
            sql.append("AND UPPER(TRIM(p.ptype)) = ? ");
            params.add(ptype.trim().toUpperCase());
        }
        if (year != null && !year.isBlank()) {
            sql.append("AND p.plcmt_year = ? ");
            params.add(year);
        }
        sql.append("GROUP BY p.dist_code, COALESCE(d.dist_name, p.dist_code) "
                 + "ORDER BY \"distName\"");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    private boolean isEmpty(Object v) {
        return v == null || v.toString().isBlank();
    }
}
