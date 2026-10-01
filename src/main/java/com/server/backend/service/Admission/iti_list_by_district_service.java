
package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.iti_list_by_district_dto;
import com.server.backend.Repository.Admission.iti_list_by_district_repository;

@Service
public class iti_list_by_district_service {

    private final iti_list_by_district_repository repository;

    public iti_list_by_district_service(iti_list_by_district_repository repository) {
        this.repository = repository;
    }

    public List<iti_list_by_district_dto> getItisByDistrict(String distCode) {

        List<Object[]> rows = repository.getItisByDistrict(distCode);

        List<iti_list_by_district_dto> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new iti_list_by_district_dto(
                    String.valueOf(row[0]),
                    String.valueOf(row[1]),
                    String.valueOf(row[2])
            ));
        }

        return result;
    }
}