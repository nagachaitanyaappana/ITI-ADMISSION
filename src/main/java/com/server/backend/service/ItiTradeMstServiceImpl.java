package com.server.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Institute.ItiTradeMstDto;
import com.server.backend.Repository.ItiTradeMstRepo;
import com.server.backend.entity.Iti_trade_mst;

@Service
public class ItiTradeMstServiceImpl
        implements ItiTradeMstService {

    private ItiTradeMstRepo repository;

    @Override
    public Iti_trade_mst createTrade(
            ItiTradeMstDto dto) {

        Iti_trade_mst trade = new Iti_trade_mst();

        trade.setTradeShort(dto.getTradeShort());
        trade.setTradeName(dto.getTradeName());
        trade.setDurationYrs(dto.getDurationYrs());
        trade.setEng_Nonengg(dto.getEngNonengg());
        trade.setMinQual(dto.getMinQual());
        trade.setTradeFreeze(dto.getTradeFreeze());
        trade.setConvApproval(dto.getConvApproval());
        trade.setTradeCode(dto.getTradeCode());
        trade.setMaxInternalMarks(dto.getMaxInternalMarks());
        trade.setTypeAdmission(dto.getTypeAdmission());
        trade.setDr_Nondr(dto.getDrNondr());
        trade.setUnitStrength(dto.getUnitStrength());
        trade.setDisplayOrder(dto.getDisplayOrder());

        return repository.save(trade);
    }

    @Override
    public List<Iti_trade_mst> getAllTrades() {
        return repository.findAll();
    }

    @Override
    public Iti_trade_mst getTradeById(
            String tradeShort) {

        return repository.findById(tradeShort)
                .orElse(null);
    }

    @Override
    public Iti_trade_mst updateTrade(
            String tradeShort,
            ItiTradeMstDto dto) {

        Iti_trade_mst trade =
                repository.findById(tradeShort)
                        .orElse(null);

        if (trade != null) {

            trade.setTradeName(dto.getTradeName());
            trade.setDurationYrs(dto.getDurationYrs());
            trade.setEng_Nonengg(dto.getEngNonengg());
            trade.setMinQual(dto.getMinQual());
            trade.setTradeFreeze(dto.getTradeFreeze());
            trade.setConvApproval(dto.getConvApproval());
            trade.setTradeCode(dto.getTradeCode());
            trade.setMaxInternalMarks(dto.getMaxInternalMarks());
            trade.setTypeAdmission(dto.getTypeAdmission());
            trade.setDr_Nondr(dto.getDrNondr());
            trade.setUnitStrength(dto.getUnitStrength());
            trade.setDisplayOrder(dto.getDisplayOrder());

            return repository.save(trade);
        }

        return null;
    }

    @Override
    public void deleteTrade(
            String tradeShort) {

        repository.deleteById(tradeShort);
    }
}