
package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti;

public interface available_trades_by_iti_repository extends JpaRepository<Iti, String> {

    @Query(value = """
            SELECT
                t.trade_short,
                m.trade_name,
                t.strength,
                t.strength_fill,
                t.strength_vacant,
                t.unit_strength
            FROM ititrade t
            JOIN ititrade_master m
              ON t.trade_short = m.trade_short
            WHERE t.iti_code = :itiCode
            """, nativeQuery = true)
    List<Object[]> getAvailableTradesByIti(@Param("itiCode") String itiCode);
}