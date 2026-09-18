
package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.ITI.Iti;

public interface eligible_iti_list_repository extends JpaRepository<Iti, String> {

    @Query(value = """
        SELECT
            i.iti_code,
            i.iti_name,
            t.trade_short,
            t.strength_vacant
        FROM iti i
        JOIN ititrade t
        ON i.iti_code = t.iti_code
        WHERE i.dist_code = :distCode
        AND t.strength_vacant > 0
        ORDER BY i.iti_name
        """, nativeQuery = true)
    List<Object[]> getEligibleItis(@Param("distCode") String distCode);
}