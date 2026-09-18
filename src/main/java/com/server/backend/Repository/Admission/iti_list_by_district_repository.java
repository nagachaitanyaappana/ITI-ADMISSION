package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.ITI.Iti;

public interface iti_list_by_district_repository extends JpaRepository<Iti, String> {

    @Query(value = """
        SELECT
            iti_code,
            iti_name,
            govt
        FROM iti
        WHERE dist_code = :distCode
        ORDER BY iti_name
        """, nativeQuery = true)
    List<Object[]> getItisByDistrict(@Param("distCode") String distCode);

}