package com.server.backend.Repository.Admission;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.iti_admissions;

public interface Print_Admission_Slip_Repository
        extends JpaRepository<iti_admissions, String> {

    Optional<iti_admissions> findByAdm_num(String adm_num);
}
