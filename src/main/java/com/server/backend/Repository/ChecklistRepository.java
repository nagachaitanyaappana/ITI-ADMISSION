package com.server.backend.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.backend.entity.Checklist;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

    List<Checklist> findByDist_code(String dist_code);


    List<Checklist> findByPhase(String phase);

    List<Checklist> findByIti_code(String iti_code);

    List<Checklist> findByApp_status(String app_status);
}