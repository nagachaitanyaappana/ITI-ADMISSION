package com.server.backend.Repository.Admission;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.iti_admissions;

public interface Print_Admission_Slip_Repository
        extends JpaRepository<iti_admissions, String> {

    @Query("SELECT a FROM iti_admissions a WHERE a.adm_num = :admNum")
    Optional<iti_admissions> findByAdmissionNumber(
            @Param("admNum") String admNum);
}