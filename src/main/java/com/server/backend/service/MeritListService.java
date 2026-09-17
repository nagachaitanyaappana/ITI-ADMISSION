package com.server.backend.service;
import java.util.List;
import com.server.backend.DTO.MeritListDTOs.MeritListRow;
import com.server.backend.DTO.MeritListDTOs.UserPrincipal;
import org.springframework.stereotype.Service;
import com.server.backend.Repository.MeritListRepository;
import com.server.backend.entity.MeritList;
import com.server.backend.entity.MeritListId;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Map;
@Service
public class MeritListService {


    private final MeritListRepository meritListRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    

    public MeritListService(MeritListRepository meritListRepository,NamedParameterJdbcTemplate jdbcTemplate) {
        this.meritListRepository = meritListRepository;
                this.jdbcTemplate = jdbcTemplate;

    }

    public List<MeritList> getAllMeritList() {
        return meritListRepository.findAll();
    }
    
    public MeritList getMeritListByRegId(Integer regid) {
        return meritListRepository.findByRegid(regid);
    }

    public List<MeritList> getMeritListByDistCode(String dist_code) {
        return meritListRepository.findByDistCode(dist_code);
    }

    public List<MeritList> getMeritListByPhase(String phase) {
        return meritListRepository.findByPhase(phase);
    }

    public List<MeritList> getMeritListByItiCode(String iti_code) {
        return meritListRepository.findByItiCode(iti_code);
    }

    public List<MeritList> getMeritListByAppStatus(String app_status) {
        return meritListRepository.findByAppStatus(app_status);
    }
    public List<MeritList> getMeritListByAppStatusIsNull() {
        return meritListRepository.findByAppStatusIsNull();
    }
    public MeritList saveMeritList(MeritList meritList) {
        return meritListRepository.save(meritList);
    }
    public MeritList updateMeritList(MeritList meritList) {
        return meritListRepository.save(meritList);
    }
    public void deleteMeritList(MeritListId id) {
        meritListRepository.deleteById(id);
    }


    
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> generateMeritList(String Category,String qual, String status, UserPrincipal user) {
        // 1. Get current year
        String yearQuery = "SELECT value FROM iti_params WHERE code = '7'";
        List<String> years = jdbcTemplate.getJdbcTemplate().queryForList(yearQuery, String.class);
        if (years.isEmpty()) {
            throw new IllegalStateException("ITI param for code '7' not found");
        }
        String year = years.get(0);

        // 1.2 Get phase details
        String phaseQuery = "SELECT phase, meritliststartdate, meritlisttodate " +
                "FROM admissions.admission_phase " +
                "WHERE year = :year AND current = 'true'";
        MapSqlParameterSource phaseParams = new MapSqlParameterSource("year", year);
        List<Map<String, Object>> phases = jdbcTemplate.queryForList(phaseQuery, phaseParams);
        if (phases.isEmpty()) {
            throw new IllegalStateException("No current phase found for year " + year);
        }

        Map<String, Object> phaseInfo = phases.get(0);
        String phase = String.valueOf(phaseInfo.get("phase"));
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

        // 2. Enforcement: for phases 2 to 5, roleid must be '4'
        String adjustedRoleId = user.roleid();
        if (List.of("2", "3", "4", "5").contains(phase)) {
            adjustedRoleId = "4";
        }

        boolean useDistCode = "3".equals(adjustedRoleId);
        String targetCode = useDistCode ? user.distCode() : user.itiCode();
        String codeColumn = useDistCode ? "dist_code" : "iti_code";

        String dbTablename;
        String heading;
        String insertColumns;
        String selectValues;

        if ("checklist".equals(status)) {
            dbTablename = "checklist";
            heading = "System Generated CheckList";
            
            // Delete from checklist where temp_pk = targetCode
            String deleteQuery = "DELETE FROM checklist WHERE temp_pk = :targetCode";
            jdbcTemplate.update(deleteQuery, new MapSqlParameterSource("targetCode", targetCode));

            insertColumns = "dist_code, regid, rank, iti_code, qual, temp_pk, phase, app_status";
            selectValues = ":distCode, regid, generated_rank::text, :itiCode, 'all', :targetCode, :phase, null";
            
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
            selectValues = ":distCode, regid, generated_rank::text, :itiCode, 'all', :targetCode, :phase, :year, null";
            
        } else if ("regeneratechecklist".equals(status)) {
            dbTablename = "checklist";
            heading = "Regenerated CHECK LIST";

            // Delete from checklist where codeColumn = targetCode
            String deleteQuery = String.format("DELETE FROM checklist WHERE %s = :targetCode", codeColumn);
            jdbcTemplate.update(deleteQuery, new MapSqlParameterSource("targetCode", targetCode));

            insertColumns = "dist_code, regid, rank, iti_code, qual, temp_pk, phase, app_status";
            selectValues = ":distCode, regid, generated_rank::text, :itiCode, 'all', :targetCode, :phase, null";
            
        } else {
            throw new IllegalArgumentException("Invalid status value provided: " + status);
        }

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
            "                ssc_tot_gpa::real DESC NULLS LAST, \n" +
            "                ssc_math_gpa::real DESC NULLS LAST, \n" +
            "                ssc_sci_gpa::real DESC NULLS LAST, \n" +
            "                ssc_social_gpa::real DESC NULLS LAST, \n" +
            "                ssc_eng_gpa::real DESC NULLS LAST, \n" +
            "                ssc_first_lang_gpa::real DESC NULLS LAST, \n" +
            "                ssc_second_lang_gpa::real DESC NULLS LAST, \n" +
            "                dob ASC, \n" +
            "                name ASC\n" +
            "        ) as generated_rank\n" +
            "    FROM (\n" +
            "        SELECT a.name, a.regid, a.dob, a.ssc_passed, b.ssc_tot_gpa, b.ssc_math_gpa, b.ssc_sci_gpa, b.ssc_social_gpa, b.ssc_eng_gpa, b.ssc_first_lang_gpa, b.ssc_second_lang_gpa\n" +
            "        FROM application a\n" +
            "        LEFT JOIN student_cand_marks b ON a.regid::character varying = b.regid\n" +
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

