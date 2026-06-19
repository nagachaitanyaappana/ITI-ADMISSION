package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.server.backend.entity.dist_master;

public interface DistrictMasterRepository extends JpaRepository<dist_master, String> {

    @Query("SELECT e.distname FROM dist_master e")
    List<String> findAllNames();

    @Query("SELECT new com.server.backend.DTO.Reports.DistrictOptionResponse(d.distcode, d.distname) FROM dist_master d")
    List<DistrictOptionResponse> findDistrictOptions();//no errors
}

