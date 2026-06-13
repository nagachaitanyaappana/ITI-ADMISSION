package com.server.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictNameResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.service.ReportService;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/districts")
    public DistrictNameResponse getDistricts() {
        return reportService.getDistricts();
    }

    @PostMapping("/districts/itis-with-trades")
    public List<ItiWithTradesResponse> getItisWithTradesByDistrict(
            @RequestBody DistrictCollegeTypeResponse districtCollegeType) {
        return reportService.getItisWithTradesByDistrict(districtCollegeType);
    }

    @GetMapping("/AboutStrive")
    public String aboutStrive() {
        return "About Strive.";
    }

    @GetMapping("/DisclosureManagement")
    public String disclosureManagement() {
        return "Disclosure Management.";
    }
}
