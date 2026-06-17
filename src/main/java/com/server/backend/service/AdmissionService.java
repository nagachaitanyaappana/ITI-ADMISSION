package com.server.backend.service;

import com.server.backend.DTO.CandidateResponseDTO;

public interface AdmissionService {

    CandidateResponseDTO getCandidateByRank(
            String rank,
            String phase,
            String year);

}