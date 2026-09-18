
package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.eligible_iti_list_dto;
import com.server.backend.Repository.Admission.eligible_iti_list_repository;

@Service
public class eligible_iti_list_service {

    private final eligible_iti_list_repository repository;

    public eligible_iti_list_service(eligible_iti_list_repository repository) {
        this.repository = repository;
    }

    public List<eligible_iti_list_dto> getEligibleItis(String distCode) {

        List<Object[]> rows = repository.getEligibleItis(distCode);

        List<eligible_iti_list_dto> result = new ArrayList<>();

        for (Object[] row : rows) {

            result.add(new eligible_iti_list_dto(
                    String.valueOf(row[0]),
                    String.valueOf(row[1]),
                    String.valueOf(row[2]),
                    row[3] == null ? null : ((Number) row[3]).intValue()
            ));
        }

        return result;
    }
}