package com.server.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictNameResponse;

<<<<<<< HEAD
import com.server.backend.Repository.ItiRepository;
import com.server.backend.Repository.DistrictMasterRepository;

=======
>>>>>>> 0dec8f87e36243e320d5689e1a1d016ff4b30061
import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.service.ReportService;

@RestController
public class ReportController {

<<<<<<< HEAD

    private final DistrictMasterRepository districtmasterrepo;
    private final ItiRepository itiRepository;
    public ReportController(DistrictMasterRepository districtmasterrepo, ItiRepository itiRepository) {
        this.districtmasterrepo = districtmasterrepo;
        this.itiRepository = itiRepository;
    }

=======
>>>>>>> 0dec8f87e36243e320d5689e1a1d016ff4b30061
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;

    }

    @GetMapping("/districts")
    public DistrictNameResponse getDistricts() {
        return reportService.getDistricts();
    }//

    @PostMapping("/districts/itis-with-trades")
    public List<ItiWithTradesResponse> getItisWithTradesByDistrict(
            @RequestBody DistrictCollegeTypeResponse districtCollegeType) {
        return reportService.getItisWithTradesByDistrict(districtCollegeType);
    }

    @GetMapping("/AboutStrive")
    public String aboutStrive() {
        return "aboutstrive";
    }

    @GetMapping("/DisclosureManagement")
    public String disclosureManagement() {
        return "Disclosure Management.";
    }
}
