package com.server.backend.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.iti;

public interface ItiRepository
        extends JpaRepository<iti,String> {

    @Query(value = "SELECT iti_name FROM iti WHERE dist_code = :dst_code", nativeQuery = true)
    List<String> findItiNamesByDistrictCode(@Param("dst_code") String dst_code);
}
