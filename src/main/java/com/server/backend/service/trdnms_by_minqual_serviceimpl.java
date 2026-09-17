package com.server.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.Repository.ItiTradeMstRepo;
import com.server.backend.entity.Iti_trade_mst;

@Service
public class trdnms_by_minqual_serviceimpl implements trdnms_by_minqual_service {

    private final ItiTradeMstRepo itiTradeMstRepo;

    public trdnms_by_minqual_serviceimpl(ItiTradeMstRepo itiTradeMstRepo) {
        this.itiTradeMstRepo = itiTradeMstRepo;
    }

    @Override
    public List<Iti_trade_mst> getTradeNamesByMinQual(String minQual) {
        return itiTradeMstRepo.findByMinQual(minQual);
    }
}

