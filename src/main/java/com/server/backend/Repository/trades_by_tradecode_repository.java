

package com.server.backend.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.Iti_trade_mst;

public interface trades_by_tradecode_repository extends JpaRepository<Iti_trade_mst, String> {

    Iti_trade_mst findByTradeCode(Integer tradeCode);

}