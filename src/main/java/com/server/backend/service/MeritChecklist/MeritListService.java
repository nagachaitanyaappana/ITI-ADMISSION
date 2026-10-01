package com.server.backend.service.MeritChecklist;
import java.util.List;
import com.server.backend.DTO.MeritListRow;
import com.server.backend.DTO.UserPrincipal;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.server.backend.Repository.MeritChecklist.MeritListRepository;
import com.server.backend.entity.RankEntity;
import com.server.backend.entity.RankId;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Map;
@Service
public class MeritListService {

    private static final Logger logger = LoggerFactory.getLogger(MeritListService.class);

    private final MeritListRepository meritListRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    

    public MeritListService(MeritListRepository meritListRepository,NamedParameterJdbcTemplate jdbcTemplate) {
        this.meritListRepository = meritListRepository;
                this.jdbcTemplate = jdbcTemplate;

    }

    public List<RankEntity> getAllMeritList() {
        return meritListRepository.findAll();
    }
    
    public RankEntity getMeritListByRegId(Integer regid) {
        return meritListRepository.findByRegid(regid);
    }

    public List<RankEntity> getMeritListByDistCode(String dist_code) {
        return meritListRepository.findByDistCode(dist_code);
    }

    public List<RankEntity> getMeritListByPhase(String phase) {
        return meritListRepository.findByPhase(phase);
    }

    public List<RankEntity> getMeritListByItiCode(String iti_code) {
        return meritListRepository.findByItiCode(iti_code);
    }

    public List<RankEntity> getMeritListByAppStatus(String app_status) {
        return meritListRepository.findByAppStatus(app_status);
    }
    public List<RankEntity> getMeritListByAppStatusIsNull() {
        return meritListRepository.findByAppStatusIsNull();
    }
    public RankEntity saveMeritList(RankEntity meritList) {
        return meritListRepository.save(meritList);
    }
    public RankEntity updateMeritList(RankEntity meritList) {
        return meritListRepository.save(meritList);
    }
    public void deleteMeritList(RankId id) {
        meritListRepository.deleteById(id);
    }


    
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> generateMeritList(String Category,String qual, String status, UserPrincipal user) {
        // 1. Resolve the year + phase actually in force.
        //    iti_params code '7' is advisory only. It currently reads 2026 while
        //    admissions.admission_phase holds its only current row under 2025, so trusting it
        //    made every request throw "No current phase found for year 2026".
        //    admission_phase is the authority, because the date-window checks below depend on it.
        String paramYearQuery = "SELECT value FROM iti_params WHERE code = '7'";
        List<String> paramYears = jdbcTemplate.getJdbcTemplate().queryForList(paramYearQuery, String.class);
        String paramYear = paramYears.isEmpty() ? null : paramYears.get(0);

        String phaseQuery = "SELECT year, phase, meritliststartdate, meritlisttodate " +
                "FROM admissions.admission_phase " +
                "WHERE current = 'true'";
        List<Map<String, Object>> phases =
                jdbcTemplate.queryForList(phaseQuery, new MapSqlParameterSource());
        if (phases.isEmpty()) {
            throw new IllegalStateException("No current admission phase found in admissions.admission_phase");
        }

        Map<String, Object> phaseInfo = phases.get(0);
        String year = String.valueOf(phaseInfo.get("year"));
        String phase = String.valueOf(phaseInfo.get("phase"));
        if (paramYear != null && !paramYear.equals(year)) {
            logger.warn("iti_params code '7' year {} does not match the current admission_phase year {}; using admission_phase",
                    paramYear, year);
        }
        Date meritliststartdate = (Date) phaseInfo.get("meritliststartdate");
        Date meritlisttodate = (Date) phaseInfo.get("meritlisttodate");

        // 1.3 Date Range Check
        Date now = new Date();
        if (meritliststartdate != null && now.before(meritliststartdate)) {
            throw new IllegalStateException(String.format("Merit list generation for phase %s has not started yet (Starts: %s)", phase, meritliststartdate));
        }
        if (meritlisttodate != null && now.after(meritlisttodate)) {
            throw new IllegalStateException(String.format("Merit list generation for phase %s has ended (Ended: %s)", phase, meritlisttodate));
        }

        // 2. The caller's own role sets the scope: role 3 = whole district, role 4 = one ITI.
        //    Phases 2-5 used to force every caller to role 4, so a district login generated a
        //    single ITI's list instead of the district's.
        String roleId = user.roleid();
        boolean useDistCode = "3".equals(roleId);
        String targetCode = useDistCode ? user.distCode() : user.itiCode();
        if (targetCode == null || targetCode.isBlank()) {
            throw new IllegalArgumentException("No " + (useDistCode ? "district" : "ITI")
                    + " code is available for this login; cannot scope the merit list.");
        }
        String codeColumn = useDistCode ? "dist_code" : "iti_code";

        // 3. 'qual' used to be ignored and every generated row was written as 'all'.
        String qualCode = (qual == null || qual.isBlank()) ? "all" : qual.trim();

        String dbTablename;
        String heading;
        String insertColumns;
        String selectValues;

        if ("checklist".equals(status)) {
            dbTablename = "checklist";
            heading = "System Generated CheckList";
            
            // checklist's primary key is regid alone (no year column), so this delete is
            // deliberately left unscoped by phase/year. Scoping it would raise duplicate-key
            // errors on the next phase instead of replacing the earlier phase's rows.
            String deleteQuery = "DELETE FROM checklist WHERE temp_pk = :targetCode";
            jdbcTemplate.update(deleteQuery, new MapSqlParameterSource("targetCode", targetCode));

            insertColumns = "dist_code, regid, rank, iti_code, qual, temp_pk, phase, app_status";
            selectValues = ":distCode, regid, generated_rank::text, :itiCode, :qual, :targetCode, :phase, null";
            
        } else if ("finalmeritlist".equals(status)) {
            // Check if checklist exists
            String checkQuery = String.format("SELECT EXISTS(SELECT 1 FROM checklist WHERE phase = :phase AND %s = :targetCode LIMIT 1)", codeColumn);
            MapSqlParameterSource checkParams = new MapSqlParameterSource()
                    .addValue("phase", phase)
                    .addValue("targetCode", targetCode);
            Boolean checklistExists = jdbcTemplate.queryForObject(checkQuery, checkParams, Boolean.class);
            if (checklistExists == null || !checklistExists) {
                throw new IllegalStateException("Checklist must be generated first for this ITI/District and Phase.");
            }

            dbTablename = "ranks";
            heading = "FINAL MERIT LIST";

            // Delete from ranks where phase = phase and codeColumn = targetCode
            String deleteQuery = String.format("DELETE FROM ranks WHERE phase = :phase AND %s = :targetCode", codeColumn);
            jdbcTemplate.update(deleteQuery, checkParams);

            insertColumns = "dist_code, regid, rank, iti_code, qual, temp_pk, phase, year, app_status";
            selectValues = ":distCode, regid, generated_rank::text, :itiCode, :qual, :targetCode, :phase, :year, null";
            
        } else if ("regeneratechecklist".equals(status)) {
            dbTablename = "checklist";
            heading = "Regenerated CHECK LIST";

            // Delete from checklist where codeColumn = targetCode
            String deleteQuery = String.format("DELETE FROM checklist WHERE %s = :targetCode", codeColumn);
            jdbcTemplate.update(deleteQuery, new MapSqlParameterSource("targetCode", targetCode));

            insertColumns = "dist_code, regid, rank, iti_code, qual, temp_pk, phase, app_status";
            selectValues = ":distCode, regid, generated_rank::text, :itiCode, :qual, :targetCode, :phase, null";
            
        } else {
            throw new IllegalArgumentException("Invalid status value provided: " + status);
        }

        // The ssc_*_gpa columns are varchar and hold '', 'NaN' and zero-padded values (e.g. '05').
        // A bare ::real aborts the whole ranking on '' (reproduced on live data) and lets 'NaN'
        // silently poison the ordering. Strip every non-numeric character first, then let
        // unparseable values fall through to NULLS LAST.
        String safeGpa = "nullif(regexp_replace(%s, '[^0-9.]', '', 'g'), '')::real";

        // Build Optimized SQL Query with safe parameters and dynamic schema interpolation
        String sql = String.format(
            "WITH existing_check AS (\n" +
            "    SELECT EXISTS (SELECT 1 FROM %s WHERE phase = :phase AND %s = :targetCode) as already_exists\n" +
            "),\n" +
            "rank_generation AS (\n" +
            "    SELECT \n" +
            "        regid, \n" +
            "        RANK() OVER (\n" +
            "            ORDER BY \n" +
            "                ssc_passed DESC, \n" +
            String.format(safeGpa, "ssc_tot_gpa") + " DESC NULLS LAST, \n" +
            String.format(safeGpa, "ssc_math_gpa") + " DESC NULLS LAST, \n" +
            String.format(safeGpa, "ssc_sci_gpa") + " DESC NULLS LAST, \n" +
            String.format(safeGpa, "ssc_social_gpa") + " DESC NULLS LAST, \n" +
            String.format(safeGpa, "ssc_eng_gpa") + " DESC NULLS LAST, \n" +
            String.format(safeGpa, "ssc_first_lang_gpa") + " DESC NULLS LAST, \n" +
            String.format(safeGpa, "ssc_second_lang_gpa") + " DESC NULLS LAST, \n" +
            "                dob ASC, \n" +
            "                name ASC\n" +
            "        ) as generated_rank\n" +
            "    FROM (\n" +
            "        SELECT a.name, a.regid, a.dob, a.ssc_passed, b.ssc_tot_gpa, b.ssc_math_gpa, b.ssc_sci_gpa, b.ssc_social_gpa, b.ssc_eng_gpa, b.ssc_first_lang_gpa, b.ssc_second_lang_gpa\n" +
            "        FROM application a\n" +
            // student_cand_marks is one row per regid (PRIMARY KEY since the 2026-09-30
            // cleanup; re-saves overwrite via upsert). DISTINCT kept as a harmless guard.
            "        LEFT JOIN (SELECT DISTINCT * FROM student_cand_marks) b ON a.regid::character varying = b.regid\n" +
            "        WHERE hstore(a.phase)->:phase = 'true'\n" +
            "        AND a.app_status = 'A'\n" +
            "        AND EXISTS (\n" +
            "            SELECT 1 FROM trade_sel t \n" +
            "            WHERE t.regid = a.regid::character varying \n" +
            "            AND t.%s = :targetCode \n" +
            "            AND hstore(t.phase)->:phase = 'true'\n" +
            "        )\n" +
            "    ) sub\n" +
            "),\n" +
            "inserted AS (\n" +
            "    INSERT INTO %s (%s)\n" +
            "    SELECT %s\n" +
            "    FROM rank_generation\n" +
            "    WHERE (SELECT NOT already_exists FROM existing_check)\n" +
            "    RETURNING rank, regid\n" +
            "),\n" +
            "final_set AS (\n" +
            "    SELECT rank::text, regid FROM inserted\n" +
            "    UNION ALL\n" +
            "    SELECT rank::text, regid FROM %s \n" +
            "    WHERE phase = :phase AND %s = :targetCode\n" +
            "    AND (SELECT already_exists FROM existing_check)\n" +
            ")\n" +
            "SELECT \n" +
            "    r.rank::text as rank, r.regid::text as regid, \n" +
            "    COALESCE(b.name, '') as name, \n" +
            "    COALESCE(b.fname, '') as fname, \n" +
            "    COALESCE(b.mname, '') as mname, \n" +
            "    COALESCE(b.gender, '') as gender, \n" +
            "    COALESCE(b.caste, '') as caste, \n" +
            "    to_char(b.dob, 'DD-MM-YYYY') as dob, \n" +
            "    CASE WHEN b.ssc_passed = 't' THEN 'Pass' ELSE 'Fail' END as ssc_passed,\n" +
            "    CASE WHEN b.phc = 't' THEN 'Yes' ELSE 'No' END as phc,\n" +
            "    CASE WHEN b.exservice = 't' THEN 'Yes' ELSE 'No' END as exservice\n" +
            "FROM final_set r\n" +
            "JOIN application b ON r.regid = b.regid\n" +
            "ORDER BY r.rank::integer;",
            dbTablename, codeColumn, codeColumn, dbTablename, insertColumns, selectValues, dbTablename, codeColumn
        );

        MapSqlParameterSource queryParams = new MapSqlParameterSource()
                .addValue("phase", phase)
                .addValue("qual", qualCode)
                .addValue("targetCode", targetCode)
                .addValue("itiCode", user.itiCode())
                .addValue("distCode", user.distCode())
                .addValue("year", year);

        List<MeritListRow> results = jdbcTemplate.query(sql, queryParams, (rs, rowNum) -> new MeritListRow(
                rs.getString("rank"),
                rs.getString("regid"),
                rs.getString("name"),
                rs.getString("fname"),
                rs.getString("mname"),
                rs.getString("gender"),
                rs.getString("caste"),
                rs.getString("dob"),
                rs.getString("ssc_passed"),
                rs.getString("phc"),
                rs.getString("exservice")
        ));

        if(results.isEmpty())
        {
            throw new IllegalStateException("No records found for the given criteria.");
        }
        return Map.of(
            "heading", heading,
            "data", results
        );
    }
}

