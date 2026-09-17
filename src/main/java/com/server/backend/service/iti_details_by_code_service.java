package com.server.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.iti_details_by_code_dto;
import com.server.backend.Repository.iti_details_by_code_repository;

@Service
public class iti_details_by_code_service {

    private final iti_details_by_code_repository repository;

    public iti_details_by_code_service(iti_details_by_code_repository repository) {
        this.repository = repository;
    }

    public List<iti_details_by_code_dto> getItiDetailsByCode(String itiCode) {

        List<Object[]> rows = repository.getItiDetailsByCode(itiCode);

        List<iti_details_by_code_dto> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new iti_details_by_code_dto(
                    (String) row[0],
                    (String) row[1],
                    String.valueOf(row[2]),   // govt
                    String.valueOf(row[3]),   // dist_code
                    (Integer) row[4],
                    (Integer) row[5],
                    (Integer) row[6],
                    (String) row[7]
            ));
        }

        return result;
    }
}