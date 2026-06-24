package com.server.backend.service;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.trades_by_tradecode_dto;
import com.server.backend.Repository.trades_by_tradecode_repository;
import com.server.backend.entity.Iti_trade_mst;

@Service
public class trades_by_tradecode_service {

    
    private trades_by_tradecode_repository repository;

    public trades_by_tradecode_dto getTradeDetails(Integer tradeCode) {

        Iti_trade_mst trade = repository.findByTradeCode(tradeCode);

        return new trades_by_tradecode_dto(
                trade.getTradeCode(),
                trade.getTradeName(),
                trade.getDurationYrs(),
                trade.getMinQual()
        );
    }
}