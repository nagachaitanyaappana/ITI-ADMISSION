package com.server.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;

import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.service.ReportService;

@RestController
public class RestReportController {

    private final ReportService reportService;

    public RestReportController(ReportService reportService) {
        this.reportService = reportService;

    }

    @PostMapping("/districts/itis-with-trades")
    public List<ItiWithTradesResponse> getItisWithTradesByDistrict(
            @RequestBody DistrictCollegeTypeResponse districtCollegeType) {
        return reportService.getItisWithTradesByDistrict(districtCollegeType);
    }

}

