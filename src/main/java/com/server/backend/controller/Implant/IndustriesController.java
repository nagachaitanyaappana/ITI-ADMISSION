package com.server.backend.controller.Implant;

import com.server.backend.DTO.IndustryDropdownResponse;
import com.server.backend.service.Implant.IndustriesService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "Implant")
@RestController
@RequestMapping("/api/placements/industries")
public class IndustriesController {

    private final IndustriesService industriesService;

    public IndustriesController(IndustriesService industriesService) {
        this.industriesService = industriesService;
    }

    @GetMapping
    public ResponseEntity<List<IndustryDropdownResponse>> getIndustries(
            @RequestParam Integer itiCode) {

        return ResponseEntity.ok(
                industriesService.getIndustriesByIti(itiCode)
        );
    }
}