package com.server.backend.service;

import java.util.ArrayList;
import java.util.List;

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
    public List<CandidateResponseDTO> getCandidatesByRank(
            String rank,
            String phase,
            String year) {

        List<RankEntity> entities = rankRepository
                .findByRankAndPhaseAndYear(rank, phase, year);

        if (entities.isEmpty()) {
            throw new RuntimeException("Candidate not found");
        }

        List<CandidateResponseDTO> dtos = new ArrayList<>();

        for (RankEntity entity : entities) {
            CandidateResponseDTO dto = new CandidateResponseDTO();
            BeanUtils.copyProperties(entity, dto);
            dtos.add(dto);
        }

        BeanUtils.copyProperties(entities.get(0), dtos.get(0));

        return dtos;
    }
}