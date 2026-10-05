package com.server.backend.Repository.Admission;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.iti_admissions;

public interface Admission_Image_Upload_Repository
        extends JpaRepository<iti_admissions, String> {

    @Query(value = """
        SELECT *
        FROM admissions.iti_admissions
        WHERE adm_num = :admissionNumber
          AND ssc_regno = :sscHallticketNumber
        LIMIT 1
        """, nativeQuery = true)
    Optional<iti_admissions> findAdmissionForImageUpload(
            @Param("admissionNumber") String admissionNumber,
            @Param("sscHallticketNumber") String sscHallticketNumber);

}
