
package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.ITI.Iti;

public interface iti_details_by_code_repository extends JpaRepository<Iti, String> {

    @Query(value = """
        SELECT iti_code, iti_name, govt, dist_code,
               capacity, allocated, remaining_capacity, address
        FROM iti
        WHERE iti_code = :itiCode
        """, nativeQuery = true)
    List<Object[]> getItiDetailsByCode(@Param("itiCode") String itiCode);
}