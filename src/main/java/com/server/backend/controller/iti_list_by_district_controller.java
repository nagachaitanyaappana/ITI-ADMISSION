package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.admission_process.iti_list_by_district_dto;
import com.server.backend.service.iti_list_by_district_service;

@RestController
@RequestMapping("/admission")
public class iti_list_by_district_controller {

    private final iti_list_by_district_service service;

    public iti_list_by_district_controller(iti_list_by_district_service service) {
        this.service = service;
    }

    @GetMapping("/district/{distCode}/itis")
    public List<iti_list_by_district_dto> getItisByDistrict(@PathVariable String distCode) {

        return service.getItisByDistrict(distCode);
    }
}