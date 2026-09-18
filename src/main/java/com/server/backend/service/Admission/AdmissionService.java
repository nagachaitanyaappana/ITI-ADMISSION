package com.server.backend.service.Admission;
import java.util.List;

import com.server.backend.DTO.CandidateResponseDTO;

public interface AdmissionService {

    List<CandidateResponseDTO> getCandidatesByRank(
            String rank,
            String phase,
            String year);

}