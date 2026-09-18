
package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.ITI.Iti;

public interface vacant_seats_by_iti_trade_repository extends JpaRepository<Iti, String> {

    @Query(value = """
        SELECT
            t.iti_code,
            t.trade_short,
            m.trade_name,
            t.strength,
            t.strength_fill,
            t.strength_vacant
        FROM ititrade t
        JOIN ititrade_master m
        ON t.trade_short = m.trade_short
        WHERE t.iti_code = :itiCode
        AND t.trade_short = :tradeShort
        """, nativeQuery = true)
    List<Object[]> getVacantSeats(
            @Param("itiCode") String itiCode,
            @Param("tradeShort") String tradeShort);
}