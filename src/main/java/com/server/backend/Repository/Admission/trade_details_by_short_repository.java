
package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti_trade_mst;

public interface trade_details_by_short_repository extends JpaRepository<Iti_trade_mst, String> {

    @Query(value = """
        SELECT trade_short,
               trade_name,
               durationyrs,
               min_qual,
               eng_nonengg,
               trade_code,
               unit_strength,
               type_admission
        FROM ititrade_master
        WHERE trade_short = :tradeShort
        """, nativeQuery = true)
    List<Object[]> getTradeDetailsByShort(@Param("tradeShort") String tradeShort);
}