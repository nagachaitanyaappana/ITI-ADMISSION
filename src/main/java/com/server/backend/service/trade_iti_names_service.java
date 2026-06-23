package com.server.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.iti.trade_iti_names_dto;
import com.server.backend.Repository.trade_iti_names_repository;

@Service
public class trade_iti_names_service {

    private final trade_iti_names_repository repository;

    public trade_iti_names_service(trade_iti_names_repository repository) {
        this.repository = repository;
    }

    public trade_iti_names_dto getTradesNamesData() {
        List<String> itiNames = repository.getAllItiNames();
        List<String> tradeNames = repository.getAllTradeNames();

        return new trade_iti_names_dto(itiNames, tradeNames);
    }
}