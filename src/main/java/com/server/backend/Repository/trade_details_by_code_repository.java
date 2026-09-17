
package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti_trade_mst;

public interface trade_details_by_code_repository extends JpaRepository<Iti_trade_mst, String> {

    @Query(value = """
        SELECT
            trade_short,
            trade_name,
            min_qual
        FROM ititrade_master
        WHERE trade_short = :tradeCode
        """, nativeQuery = true)
    List<Object[]> getTradeDetailsByCode(@Param("tradeCode") String tradeCode);

}