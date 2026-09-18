
package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.trade_details_by_code_dto;
import com.server.backend.Repository.Admission.trade_details_by_code_repository;

@Service
public class trade_details_by_code_service {

    private final trade_details_by_code_repository repository;

    public trade_details_by_code_service(trade_details_by_code_repository repository) {
        this.repository = repository;
    }

    public List<trade_details_by_code_dto> getTradeDetailsByCode(String tradeCode) {

        List<Object[]> rows = repository.getTradeDetailsByCode(tradeCode);

        List<trade_details_by_code_dto> result = new ArrayList<>();

        for (Object[] row : rows) {

            result.add(new trade_details_by_code_dto(
                    String.valueOf(row[0]),
                    String.valueOf(row[1]),
                    String.valueOf(row[2])
            ));
        }

        return result;
    }
}