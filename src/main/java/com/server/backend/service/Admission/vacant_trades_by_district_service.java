
package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.vacant_trades_by_district_dto;
import com.server.backend.Repository.Admission.vacant_trades_by_district_repository;

@Service
public class vacant_trades_by_district_service {

    private final vacant_trades_by_district_repository repository;

    public vacant_trades_by_district_service(vacant_trades_by_district_repository repository) {
        this.repository = repository;
    }

    public List<vacant_trades_by_district_dto> getVacantTradesByDistrict(String distCode) {

        List<Object[]> rows = repository.getVacantTradesByDistrict(distCode);

        List<vacant_trades_by_district_dto> result = new ArrayList<>();

        for (Object[] row : rows) {

            result.add(new vacant_trades_by_district_dto(
                    String.valueOf(row[0]),
                    String.valueOf(row[1]),
                    String.valueOf(row[2]),
                    String.valueOf(row[3]),
                    ((Number) row[4]).intValue()
            ));
        }

        return result;
    }
}