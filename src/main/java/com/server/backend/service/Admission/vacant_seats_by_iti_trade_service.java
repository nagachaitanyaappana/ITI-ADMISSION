
package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.iti.vacant_seats_by_iti_trade_dto;
import com.server.backend.Repository.Admission.vacant_seats_by_iti_trade_repository;

@Service
public class vacant_seats_by_iti_trade_service {

    private final vacant_seats_by_iti_trade_repository repository;

    public vacant_seats_by_iti_trade_service(vacant_seats_by_iti_trade_repository repository) {
        this.repository = repository;
    }

    public List<vacant_seats_by_iti_trade_dto> getVacantSeats(String itiCode, String tradeShort) {

        List<Object[]> rows = repository.getVacantSeats(itiCode, tradeShort);

        List<vacant_seats_by_iti_trade_dto> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new vacant_seats_by_iti_trade_dto(
                    String.valueOf(row[0]),
                    String.valueOf(row[1]),
                    String.valueOf(row[2]),
                    row[3] == null ? null : ((Number) row[3]).intValue(),
                    row[4] == null ? null : ((Number) row[4]).intValue(),
                    row[5] == null ? null : ((Number) row[5]).intValue()
            ));
        }

        return result;
    }
}