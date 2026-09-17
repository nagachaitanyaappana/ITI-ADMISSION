
package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti_trade_mst;

public interface trades_by_iti_qualification_repository extends JpaRepository<Iti_trade_mst, String> {

    @Query(value = """
        SELECT
            t.trade_short,
            m.trade_name,
            m.min_qual
        FROM ititrade t
        JOIN ititrade_master m
        ON t.trade_short = m.trade_short
        WHERE t.iti_code = :itiCode
        AND m.min_qual = :minQual
        ORDER BY m.trade_name
        """, nativeQuery = true)
    List<Object[]> getTradesByQualification(
            @Param("itiCode") String itiCode,
            @Param("minQual") String minQual);

}