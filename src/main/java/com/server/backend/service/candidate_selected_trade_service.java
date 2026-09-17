
    package com.server.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.candidate_selected_trade_dto;
import com.server.backend.Repository.candidate_selected_trade_repository;

@Service
public class candidate_selected_trade_service {

    private final candidate_selected_trade_repository repository;

    public candidate_selected_trade_service(
            candidate_selected_trade_repository repository) {

        this.repository = repository;
    }

    public List<candidate_selected_trade_dto> getCandidateSelectedTrade(Integer regId) {

        List<Object[]> rows = repository.getCandidateSelectedTrade(regId);

        List<candidate_selected_trade_dto> result = new ArrayList<>();

        for (Object[] row : rows) {

            result.add(new candidate_selected_trade_dto(

                    (Integer) row[0],
                    String.valueOf(row[1]),
                    String.valueOf(row[2]),
                    String.valueOf(row[3]),
                    String.valueOf(row[4]),
                    String.valueOf(row[5]),
                    (Integer) row[6]

            ));
        }

        return result;
    }
}

