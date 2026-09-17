
package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.admission_process.trades_by_iti_qualification_dto;
import com.server.backend.service.trades_by_iti_qualification_service;

@RestController
@RequestMapping("/admission")
public class trades_by_iti_qualification_controller {

    private final trades_by_iti_qualification_service service;

    public trades_by_iti_qualification_controller(trades_by_iti_qualification_service service) {
        this.service = service;
    }

    @GetMapping("/iti/{itiCode}/trades/{minQual}")
    public List<trades_by_iti_qualification_dto> getTradesByQualification(
            @PathVariable String itiCode,
            @PathVariable String minQual) {

        return service.getTradesByQualification(itiCode, minQual);
    }
}