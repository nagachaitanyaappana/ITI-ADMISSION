
package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.seat_details_by_iti_dto;
import com.server.backend.Repository.Admission.seat_details_by_iti_repository;

@Service
public class seat_details_by_iti_service {

    private final seat_details_by_iti_repository repository;

    public seat_details_by_iti_service(seat_details_by_iti_repository repository) {
        this.repository = repository;
    }

    public List<seat_details_by_iti_dto> getSeatDetailsByIti(String itiCode) {

        List<Object[]> rows = repository.getSeatDetailsByIti(itiCode);

        List<seat_details_by_iti_dto> result = new ArrayList<>();

        for (Object[] row : rows) {

            result.add(new seat_details_by_iti_dto(
                    String.valueOf(row[0]),
                    ((Number) row[1]).intValue(),
                    ((Number) row[2]).intValue(),
                    ((Number) row[3]).intValue()
            ));
        }

        return result;
    }
}