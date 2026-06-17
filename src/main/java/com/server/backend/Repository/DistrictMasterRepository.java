package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.dist_master;

public interface DistrictMasterRepository extends JpaRepository<dist_master, String> {

    @Query("SELECT e.distname FROM dist_master e")
    List<String> findAllNames();

    @Query(value = "SELECT dist_code FROM dist_mst WHERE dist_name = :distName LIMIT 1", nativeQuery = true)
    String findCodeByDistrictName(@Param("distName") String distName);
}
