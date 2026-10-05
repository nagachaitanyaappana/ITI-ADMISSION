package com.server.backend.Repository.Admission;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.iti_admissions;

public interface Admission_Update_Repository
        extends JpaRepository<iti_admissions, String> {

    @Query(value = """
        SELECT *
        FROM admissions.iti_admissions
        WHERE adm_num = :admissionNumber
        LIMIT 1
        """, nativeQuery = true)
    Optional<iti_admissions> findByAdmissionNumber(
            @Param("admissionNumber") String admissionNumber);

}
