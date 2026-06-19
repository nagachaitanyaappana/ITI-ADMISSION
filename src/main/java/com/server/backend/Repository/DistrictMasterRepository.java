package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.server.backend.entity.dist_master;

<<<<<<< HEAD
public interface DistrictMasterRepository extends JpaRepository<dist_master, String> {

    @Query("SELECT e.distname FROM dist_master e")
    List<String> findAllNames();

    @Query(value = "SELECT dist_code FROM dist_mst WHERE dist_name = :distName LIMIT 1", nativeQuery = true)
    String findCodeByDistrictName(@Param("distName") String distName);
      



}

=======

public interface DistrictMasterRepository extends JpaRepository<dist_master, String> {

    @Query("SELECT e.dist_name FROM dist_master e") 
    List<String> findAllNames();

   @Query("SELECT new com.server.backend.DTO.Reports.DistrictMasterResponse(d.dist_code, d.dist_name) FROM dist_master d")
    List<DistrictMasterResponse> findAllDistrictsDTO();
}
>>>>>>> 0eee2122799913d8898e98328879e565850b0a59
