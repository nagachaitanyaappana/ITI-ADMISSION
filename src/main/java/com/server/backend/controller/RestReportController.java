package com.server.backend.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.service.ReportService;

@RestController
public class RestReportController {

    private final ReportService reportService;

    public RestReportController(ReportService reportService) {
        this.reportService = reportService;

    }

    @PostMapping("/trade_display2")
    public String submitTradeDisplay(
        @RequestParam("dist_code") String distCode, 
        @RequestParam("gov") String type, 
        Model model) {
    
    // Create the DTO using your existing class
    DistrictCollegeTypeResponse request = new DistrictCollegeTypeResponse();
    request.setDist(distCode); // Now passing the code directly
    request.setType(type);
    
    // Fetch data using your existing Service method
    List<ItiWithTradesResponse> results = reportService.getItisWithTradesByDistrict(request);
    
    // Pass results to view
    model.addAttribute("itiList", results);
    model.addAttribute("districtList", reportService.getAllDistricts());
    
    return "tradedisplay";
}
}

