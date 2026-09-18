package com.server.backend.Repository.Student;

import com.server.backend.entity.AdmissionPhase;
import com.server.backend.entity.AdmissionPhaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionPhaseRepository extends JpaRepository<AdmissionPhase, AdmissionPhaseId> {

}