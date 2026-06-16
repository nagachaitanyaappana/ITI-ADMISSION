package com.server.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.CandidateResponseDTO;
import com.server.backend.service.AdmissionService;

@RestController
@RequestMapping("/admission")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @GetMapping("/candidate")
    public CandidateResponseDTO getCandidateByRank(
            @RequestParam Integer rank,
            @RequestParam Integer phase,
            @RequestParam Integer year) {

        return admissionService.getCandidateByRank(rank, phase, year);
    }
}