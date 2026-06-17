package com.server.backend.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.Checklist;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

    List<Checklist> findByDistCode(@Param("dist_code") String dist_code);


    List<Checklist> findByPhase(@Param("phase") String phase);

    List<Checklist> findByItiCode(@Param("iti_code") String iti_code);

    List<Checklist> findByAppStatus(@Param("app_status") String app_status);
}