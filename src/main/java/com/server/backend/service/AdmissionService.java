package com.server.backend.service;

import com.server.backend.DTO.CandidateResponseDTO;

public interface AdmissionService {

    CandidateResponseDTO getCandidateByRank(
            Integer rank,
            Integer phase,
            Integer year);

}