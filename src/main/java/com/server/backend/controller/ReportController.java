package com.server.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictNameResponse;

import com.server.backend.DTO.Reports.ITINameResponse;
import com.server.backend.Repository.ItiRepository;
import com.server.backend.Repository.DistrictMasterRepository;

import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.service.ReportService;

@RestController
public class ReportController
 {

    private final DistrictMasterRepository districtmasterrepo;
    private final ItiRepository itiRepository;
    public ReportController(DistrictMasterRepository districtmasterrepo, ItiRepository itiRepository) {
      {  this.districtmasterrepo = districtmasterrepo;
        this.itiRepository = itiRepository;
        }

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
        return "aboutStrive";
    }

    @GetMapping("/DisclosureManagement")
    public String disclosureManagement() {
        return "disclosureManagement";
    }
}
