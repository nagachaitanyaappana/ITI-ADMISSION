package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.backend.entity.Checklist;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, String> {

    List<Checklist> findByDistCode(String distCode);

    List<Checklist> findByPhase(String phase);

    List<Checklist> findByItiCode(String itiCode);

    List<Checklist> findByAppStatus(String appStatus);
}