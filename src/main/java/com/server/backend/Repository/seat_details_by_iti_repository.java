
package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti;

public interface seat_details_by_iti_repository extends JpaRepository<Iti, String> {

    @Query(value = """
        SELECT
            trade_short,
            strength,
            strength_fill,
            strength_vacant
        FROM ititrade
        WHERE iti_code = :itiCode
        """, nativeQuery = true)
    List<Object[]> getSeatDetailsByIti(@Param("itiCode") String itiCode);

}