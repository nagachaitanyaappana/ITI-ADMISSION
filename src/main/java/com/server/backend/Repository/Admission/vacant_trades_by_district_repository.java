
package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.ITI.Iti;

public interface vacant_trades_by_district_repository extends JpaRepository<Iti, String> {

    @Query(value = """
        SELECT
            i.iti_code,
            i.iti_name,
            t.trade_short,
            m.trade_name,
            t.strength_vacant
        FROM iti i
        JOIN ititrade t
          ON i.iti_code = t.iti_code
        JOIN ititrade_master m
          ON t.trade_short = m.trade_short
        WHERE i.dist_code = :distCode
          AND t.strength_vacant > 0
        ORDER BY i.iti_name, m.trade_name
        """, nativeQuery = true)
    List<Object[]> getVacantTradesByDistrict(@Param("distCode") String distCode);

}
