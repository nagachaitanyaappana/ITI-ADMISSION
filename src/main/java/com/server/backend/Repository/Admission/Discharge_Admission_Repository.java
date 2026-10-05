package com.server.backend.Repository.Admission;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.iti_admissions;

public interface Discharge_Admission_Repository
        extends JpaRepository<iti_admissions, String> {

    @Query(value = """
        SELECT *
        FROM admissions.iti_admissions
        WHERE TRIM(BOTH FROM adm_num) = TRIM(BOTH FROM :admNum)
          AND TRIM(BOTH FROM CAST(year_of_admission AS TEXT)) = TRIM(BOTH FROM :year)
        LIMIT 1
        """, nativeQuery = true)
    Optional<iti_admissions> findByAdmissionNumberAndYear(
            @Param("admNum") String admNum,
            @Param("year") String year);
}