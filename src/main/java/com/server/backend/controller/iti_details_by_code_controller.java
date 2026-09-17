
package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.admission_process.iti_details_by_code_dto;
import com.server.backend.service.iti_details_by_code_service;

@RestController
@RequestMapping("/admission")
public class iti_details_by_code_controller {

    private final iti_details_by_code_service service;

    public iti_details_by_code_controller(iti_details_by_code_service service) {
        this.service = service;
    }

    @GetMapping("/iti/{itiCode}")
    public List<iti_details_by_code_dto> getItiDetailsByCode(@PathVariable String itiCode) {
        return service.getItiDetailsByCode(itiCode);
    }
}