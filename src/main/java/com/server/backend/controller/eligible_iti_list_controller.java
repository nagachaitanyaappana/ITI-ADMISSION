
package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.admission_process.eligible_iti_list_dto;
import com.server.backend.service.eligible_iti_list_service;

@RestController
@RequestMapping("/admission")
public class eligible_iti_list_controller {

    private final eligible_iti_list_service service;

    public eligible_iti_list_controller(eligible_iti_list_service service) {
        this.service = service;
    }

    @GetMapping("/eligible-itis/{distCode}")
    public List<eligible_iti_list_dto> getEligibleItis(@PathVariable String distCode) {
        return service.getEligibleItis(distCode);
    }
}