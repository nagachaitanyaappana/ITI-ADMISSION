package com.server.backend.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.admission_process.candidate_profile_dto;
import com.server.backend.service.candidate_profile_service;

@Tag(name = "Admission Process")
@RestController
@RequestMapping("/admission")
public class candidate_profile_controller {

    private final candidate_profile_service service;

    public candidate_profile_controller(candidate_profile_service service) {
        this.service = service;
    }

    @GetMapping("/candidate/{regId}")
    public List<candidate_profile_dto> getCandidateProfile(@PathVariable Integer regId) {

        return service.getCandidateProfile(regId);
    }
}