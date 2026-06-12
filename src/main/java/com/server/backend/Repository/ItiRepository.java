package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.iti;

public interface ItiRepository extends JpaRepository<iti, String> {

    @Query(value = "SELECT iti_name FROM iti WHERE dist_code = :distCode AND govt = :govt",nativeQuery = true)
    List<String> findItiNamesByDistrictCodeAndGovt(@Param("distCode") String distCode,@Param("govt") String govt);

    @Query(value = "SELECT iti_name FROM iti WHERE dist_code = :distCode",nativeQuery = true)
    List<String> findItiNamesByDistrictCode(@Param("distCode") String distCode);
}