package com.server.backend.service.ITI;

import java.util.List;

import com.server.backend.DTO.ItiTradeMstDto;
import com.server.backend.entity.ITI.Iti_trade_mst;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;


public interface ItiTradeMstService {


  Iti_trade_mst createTrade(@Valid @RequestBody ItiTradeMstDto dto);


    List<Iti_trade_mst> getAllTrades();

    Iti_trade_mst getTradeById(String tradeShort);

    Iti_trade_mst updateTrade(
            String tradeShort,
            ItiTradeMstDto dto);


    void deleteTrade(String tradeShort);

}