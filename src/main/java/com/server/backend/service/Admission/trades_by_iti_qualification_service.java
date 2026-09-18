package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.trades_by_iti_qualification_dto;
import com.server.backend.Repository.Admission.trades_by_iti_qualification_repository;

@Service
public class trades_by_iti_qualification_service {

    private final trades_by_iti_qualification_repository repository;

    public trades_by_iti_qualification_service(trades_by_iti_qualification_repository repository) {
        this.repository = repository;
    }

    public List<trades_by_iti_qualification_dto> getTradesByQualification(String itiCode, String minQual) {

        List<Object[]> rows = repository.getTradesByQualification(itiCode, minQual);

        List<trades_by_iti_qualification_dto> result = new ArrayList<>();

        for (Object[] row : rows) {

            result.add(new trades_by_iti_qualification_dto(
                    String.valueOf(row[0]),
                    String.valueOf(row[1]),
                    String.valueOf(row[2])
            ));
        }

        return result;
    }
}