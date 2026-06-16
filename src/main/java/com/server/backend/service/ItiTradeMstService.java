package com.server.backend.service;

import java.util.List;

import com.server.backend.DTO.Institute.ItiTradeMstDto;
import com.server.backend.entity.Iti_trade_mst;

public interface ItiTradeMstService {

<<<<<<< HEAD
    Iti_trade_mst createTrade(ItiTradeMstDto dto);
=======
  Iti_trade_mst createTrade(ItiTradeMstDto dto);
>>>>>>> 1cccb784b067079244ee144cbdc9d8f01f50c3ce

    List<Iti_trade_mst> getAllTrades();

    Iti_trade_mst getTradeById(String tradeShort);

    Iti_trade_mst updateTrade(
            String tradeShort,
            ItiTradeMstDto dto);

<<<<<<< HEAD
    void deleteTrade(String tradeShort);
=======
   void deleteTrade(String tradeShort);
>>>>>>> 1cccb784b067079244ee144cbdc9d8f01f50c3ce
}