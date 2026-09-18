package com.server.backend.controller.Admission;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.admission_process.candidate_selected_trade_dto;
import com.server.backend.service.Admission.candidate_selected_trade_service;

@Tag(name = "Admission Process")
@RestController
public class candidate_selected_trade_controller {

    private final candidate_selected_trade_service service;

    public candidate_selected_trade_controller(
            candidate_selected_trade_service service) {
        this.service = service;
    }

    @GetMapping("/candidate-selected-trade/{regId}")
    public List<candidate_selected_trade_dto> getCandidateSelectedTrade(
            @PathVariable Integer regId) {

        return service.getCandidateSelectedTrade(regId);
    }
}
