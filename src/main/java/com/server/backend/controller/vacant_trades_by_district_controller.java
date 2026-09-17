
package com.server.backend.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.admission_process.vacant_trades_by_district_dto;
import com.server.backend.service.vacant_trades_by_district_service;

@Tag(name = "Admission Process")
@RestController
@RequestMapping("/admission")
public class vacant_trades_by_district_controller {

    private final vacant_trades_by_district_service service;

    public vacant_trades_by_district_controller(vacant_trades_by_district_service service) {
        this.service = service;
    }

    @GetMapping("/district/{distCode}/vacant-trades")
    public List<vacant_trades_by_district_dto> getVacantTradesByDistrict(
            @PathVariable String distCode) {

        return service.getVacantTradesByDistrict(distCode);
    }
}