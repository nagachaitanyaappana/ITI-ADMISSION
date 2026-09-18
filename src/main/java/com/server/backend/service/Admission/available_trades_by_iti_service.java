
package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.available_trades_by_iti_dto;
import com.server.backend.Repository.Admission.available_trades_by_iti_repository;

@Service
public class available_trades_by_iti_service {

    private final available_trades_by_iti_repository repository;

    public available_trades_by_iti_service(available_trades_by_iti_repository repository) {
        this.repository = repository;
    }

    public List<available_trades_by_iti_dto> getAvailableTradesByIti(String itiCode) {

        List<Object[]> rows = repository.getAvailableTradesByIti(itiCode);

        List<available_trades_by_iti_dto> result = new ArrayList<>();

        for (Object[] row : rows) {

            result.add(new available_trades_by_iti_dto(
                    String.valueOf(row[0]),
                    String.valueOf(row[1]),
                    row[2] == null ? null : ((Number) row[2]).intValue(),
                    row[3] == null ? null : ((Number) row[3]).intValue(),
                    row[4] == null ? null : ((Number) row[4]).intValue(),
                    row[5] == null ? null : ((Number) row[5]).intValue()
            ));
        }

        return result;
    }
}