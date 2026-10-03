package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.AdmissionTiming;

public interface Admission_Timing_Repository
        extends JpaRepository<AdmissionTiming, Long> {

    List<AdmissionTiming> findByMinqulAndCasteAndPhaseAndYear(
            String minqul,
            String caste,
            String phase,
            String year);
}