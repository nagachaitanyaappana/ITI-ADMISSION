
package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.iti.trades_by_eng_nonengg_dto;
import com.server.backend.Repository.Admission.trades_by_eng_nonengg_repository;

@Service
public class trades_by_eng_nonengg_service {

    private final trades_by_eng_nonengg_repository repository;

    public trades_by_eng_nonengg_service(trades_by_eng_nonengg_repository repository) {
        this.repository = repository;
    }

    public List<trades_by_eng_nonengg_dto> getTradeNamesByType(String type) {

        List<String> tradeNames = repository.getTradeNamesByType(type);

        List<trades_by_eng_nonengg_dto> dtoList = new ArrayList<>();

        for (String tradeName : tradeNames) {
            dtoList.add(new trades_by_eng_nonengg_dto(tradeName));
        }

        return dtoList;
    }
}