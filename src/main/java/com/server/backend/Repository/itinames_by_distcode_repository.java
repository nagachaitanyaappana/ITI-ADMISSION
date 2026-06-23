package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti;

public interface itinames_by_distcode_repository extends JpaRepository<Iti, String> {

    @Query(value = "SELECT iti_name FROM iti WHERE dist_code = :distCode", nativeQuery = true)
    List<String> getItiNamesByDistrict(@Param("distCode") String distCode);
}
