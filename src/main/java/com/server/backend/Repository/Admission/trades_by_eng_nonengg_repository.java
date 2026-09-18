
package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.ITI.Iti_trade_mst;

public interface trades_by_eng_nonengg_repository extends JpaRepository<Iti_trade_mst, String> {

    @Query(value = "SELECT trade_name FROM ititrade_master WHERE eng_nonengg = :type", nativeQuery = true)
    List<String> getTradeNamesByType(@Param("type") String type);

}