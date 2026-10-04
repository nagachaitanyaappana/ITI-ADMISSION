package com.server.backend.service.Admission;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Call_Letter_DTO;
import com.server.backend.Repository.Admission.Call_Letter_Repository;

@Service
public class Call_Letter_ServiceImpl
        implements Call_Letter_Service {

    private final Call_Letter_Repository repository;

    public Call_Letter_ServiceImpl(
            Call_Letter_Repository repository) {

        this.repository = repository;
    }

    @Override
    public List<Call_Letter_DTO> getCallLetterData(
            String rank,
            String caste) {

        List<Object[]> results =
                repository.findCallLetterData(rank, caste);

        List<Call_Letter_DTO> response =
                new ArrayList<>();

        for (Object[] row : results) {

            String rankValue =
                    row[0] != null ? row[0].toString() : null;

            String casteValue =
                    row[1] != null ? row[1].toString() : null;

            Integer regid =
                    row[2] != null
                            ? ((Number) row[2]).intValue()
                            : null;

            String itiCode =
                    row[3] != null ? row[3].toString() : null;

            String qualification =
                    row[4] != null ? row[4].toString() : null;

            String tempPk =
                    row[5] != null ? row[5].toString() : null;

            String phase =
                    row[6] != null ? row[6].toString() : null;

            Long trno =
                    row[7] != null
                            ? ((Number) row[7]).longValue()
                            : null;

            String year =
                    row[8] != null ? row[8].toString() : null;

            String name =
                    row[9] != null ? row[9].toString() : null;

            Call_Letter_DTO dto =
                    new Call_Letter_DTO(
                            rankValue,
                            casteValue,
                            regid,
                            itiCode,
                            qualification,
                            tempPk,
                            phase,
                            trno,
                            year,
                            name
                    );

            response.add(dto);
        }

        return response;
    }

}
