package com.server.backend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.CandidateResponseDTO;
import com.server.backend.Repository.RankRepository;
import com.server.backend.entity.RankEntity;

@Service
public class AdmissionServiceImpl implements AdmissionService {

    private final RankRepository rankRepository;

    public AdmissionServiceImpl(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @Override
    public CandidateResponseDTO getCandidateByRank(
            String rank,
            String phase,
            String year) {

        RankEntity entity = rankRepository
                .findByRankAndPhaseAndYear(rank, phase, year)
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));

        CandidateResponseDTO dto = new CandidateResponseDTO();

        BeanUtils.copyProperties(entity, dto);

        return dto;
    }
}