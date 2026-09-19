package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.TradeSel2025Phase1;

public interface candidate_selected_trade_repository
        extends JpaRepository<TradeSel2025Phase1, Integer> {

    @Query(value = """
            SELECT
                regid,
                iti_code,
                temp_code,
                dist_code,
                phase,
                "year",
                "freeze"
            FROM trade_sel2025phase1
            WHERE regid = :regId
            """, nativeQuery = true)
    List<Object[]> getCandidateSelectedTrade(
            @Param("regId") Integer regId);

}