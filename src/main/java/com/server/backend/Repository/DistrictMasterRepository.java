package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.server.backend.DTO.Reports.DistrictMasterResponse;
import com.server.backend.entity.dist_master;

package com.server.backend.Repository;

public interface DistrictMasterRepository extends JpaRepository<dist_master, String> {

    @Query("SELECT e.dist_name FROM dist_master e") // Adjusted to your entity field name
    List<String> findAllNames();

    // Use the exact Entity class name (dist_master) and the fields defined in it
    @Query("SELECT new com.server.backend.DTO.Reports.DistrictMasterResponse(d.dist_code, d.dist_name) FROM dist_master d")
   List<DistrictMasterResponse> findAllDistrictsDTO();
}