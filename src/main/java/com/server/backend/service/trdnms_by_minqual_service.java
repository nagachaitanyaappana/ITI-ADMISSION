 package com.server.backend.service;

import java.util.List;

import com.server.backend.entity.ITI.Iti_trade_mst;

public interface trdnms_by_minqual_service {

    List<Iti_trade_mst> getTradeNamesByMinQual(String minQual);

}

