package com.server.backend.Repository.PlacementsRepositories;
import com.server.backend.entity.Placements.IndustryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import java.lang.Long;
import java.util.List;
public interface IndustryMasterRepository extends JpaRepository<IndustryMaster, Long> {
    List<IndustryMaster> findByIndustryId(Long industryId);
    
}

