package com.server.backend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.backend.entity.AdmissionTiming;
public interface AdmissionTimingRepository extends JpaRepository<AdmissionTiming, String> {
    
}
