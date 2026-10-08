package com.server.backend.Repository.Admission;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.iti_admissions;

public interface Dsc_List_Repository
        extends JpaRepository<iti_admissions, String> {

    @Query(value = """
        SELECT
            adm_num,
            name,
            fname,
            gender,
            dob,
            caste
        FROM admissions.iti_admissions
        WHERE iti_code = :itiCode
          AND trade_code = :tradeCode
          AND phase = :phase
          AND year_of_admission = :year
        ORDER BY adm_num
        """, nativeQuery = true)
    List<Object[]> findDscList(
            @Param("itiCode") String itiCode,
            @Param("tradeCode") Integer tradeCode,
            @Param("phase") Integer phase,
            @Param("year") String year);

}
