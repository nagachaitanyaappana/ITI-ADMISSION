package com.server.backend.Repository.Admission;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.iti_admissions;

public interface Admission_Search_Repository
        extends JpaRepository<iti_admissions, String> {

    @Query(value = """
        SELECT *
        FROM admissions.iti_admissions
        WHERE (:admissionNumber IS NULL OR adm_num = :admissionNumber)
          AND (:registrationNumber IS NULL OR regno = :registrationNumber)
          AND (:name IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:fatherName IS NULL OR LOWER(fname) LIKE LOWER(CONCAT('%', :fatherName, '%')))
          AND (:phoneNumber IS NULL OR phno = :phoneNumber)
          AND (:gender IS NULL OR gender = :gender)
          AND (:caste IS NULL OR caste = :caste)
          AND (:reservationCategory IS NULL OR res_category = :reservationCategory)
          AND (:yearOfAdmission IS NULL OR year_of_admission = :yearOfAdmission)
          AND (:dateOfAdmission IS NULL OR date_of_admission = :dateOfAdmission)
          AND (:typeOfAdmission IS NULL OR type_admission = :typeOfAdmission)
          AND (:currentSem IS NULL OR current_sem = :currentSem)
          AND (:districtCode IS NULL OR dist_code = :districtCode)
          AND (:itiCode IS NULL OR iti_code = :itiCode)
          AND (:tradeCode IS NULL OR trade_code = :tradeCode)
          AND (:phase IS NULL OR phase = :phase)
        ORDER BY adm_num
        """,
        nativeQuery = true)
    List<iti_admissions> searchAdmissions(

            @Param("admissionNumber")
            String admissionNumber,

            @Param("registrationNumber")
            String registrationNumber,

            @Param("name")
            String name,

            @Param("fatherName")
            String fatherName,

            @Param("phoneNumber")
            Long phoneNumber,

            @Param("gender")
            String gender,

            @Param("caste")
            String caste,

            @Param("reservationCategory")
            String reservationCategory,

            @Param("yearOfAdmission")
            String yearOfAdmission,

            @Param("dateOfAdmission")
            LocalDate dateOfAdmission,

            @Param("typeOfAdmission")
            String typeOfAdmission,

            @Param("currentSem")
            String currentSem,

            @Param("districtCode")
            String districtCode,

            @Param("itiCode")
            String itiCode,

            @Param("tradeCode")
            Integer tradeCode,

            @Param("phase")
            Integer phase);

}
