package com.server.backend.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.Checklist;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

    List<Checklist> findByDistCode(String dist_code);


    List<Checklist> findByPhase(String phase);

    List<Checklist> findByItiCode(String iti_code);

    List<Checklist> findByAppStatus(String app_status);
}