
package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.trade_details_by_short_dto;
import com.server.backend.Repository.Admission.trade_details_by_short_repository;

@Service
public class trade_details_by_short_service {

    private final trade_details_by_short_repository repository;

    public trade_details_by_short_service(trade_details_by_short_repository repository) {
        this.repository = repository;
    }

    public List<trade_details_by_short_dto> getTradeDetailsByShort(String tradeShort) {

        List<Object[]> rows = repository.getTradeDetailsByShort(tradeShort);

        List<trade_details_by_short_dto> result = new ArrayList<>();

        for (Object[] row : rows) {

            result.add(new trade_details_by_short_dto(
                    String.valueOf(row[0]),
                    String.valueOf(row[1]),
                    row[2] == null ? null : ((Number) row[2]).intValue(),
                    String.valueOf(row[3]),
                    String.valueOf(row[4]),
                    row[5] == null ? null : ((Number) row[5]).intValue(),
                    row[6] == null ? null : ((Number) row[6]).intValue(),
                    String.valueOf(row[7])
            ));
        }

        return result;
    }
}