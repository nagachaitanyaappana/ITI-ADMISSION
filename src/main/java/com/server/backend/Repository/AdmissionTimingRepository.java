package com.server.backend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.backend.entity.AdmissionTiming;
import com.server.backend.entity.AdmissionTimingId;
public interface AdmissionTimingRepository extends JpaRepository<AdmissionTiming, AdmissionTimingId> {
    
}
