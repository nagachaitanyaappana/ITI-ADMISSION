package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.CandidateResponseDTO;
import com.server.backend.service.AdmissionService;

@RestController
@RequestMapping("/admission")
public class RankController {

    private final AdmissionService admissionService;

    public RankController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @GetMapping("/candidate")
    public List<CandidateResponseDTO> getCandidatesByRank(
            @RequestParam String rank,
            @RequestParam String phase,
            @RequestParam String year) {

        return admissionService.getCandidatesByRank(rank, phase, year);
    }
}