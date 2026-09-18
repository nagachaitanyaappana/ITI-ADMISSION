package com.server.backend.service.Admission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.admission_process.candidate_profile_dto;
import com.server.backend.Repository.Admission.candidate_profile_repository;

@Service
public class candidate_profile_service {

    private final candidate_profile_repository repository;

    public candidate_profile_service(candidate_profile_repository repository) {
        this.repository = repository;
    }

    public List<candidate_profile_dto> getCandidateProfile(Integer regId) {

        List<Object[]> rows = repository.getCandidateProfile(regId);

        List<candidate_profile_dto> result = new ArrayList<>();

        for (Object[] row : rows) {

            result.add(new candidate_profile_dto(
                    String.valueOf(row[0]),
                    String.valueOf(row[1]),
                    String.valueOf(row[2]),
                    String.valueOf(row[3]),
                    String.valueOf(row[4])
            ));
        }

        return result;
    }
}