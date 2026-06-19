package com.server.backend.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.service.ReportService;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;

    }

   @GetMapping("/tradedisplay")
    public String tradedisplay(Model model) {
        model.addAttribute("districtList", reportService.getAllDistricts());
        return "tradedisplay"; 
    }

    
    @PostMapping("/trade_display2")
    public String submitTradeDisplay(@ModelAttribute DistrictCollegeTypeResponse request, Model model) {
    
        // Add these print statements to check what the form sent
        System.out.println("DTO received - Dist: " + request.getDist() + ", Type: " + request.getType());

        List<ItiWithTradesResponse> results = reportService.getItisWithTradesByDistrict(request);

        if (results == null) {
            results = new java.util.ArrayList<>();
        }

        model.addAttribute("itiList", results);
        model.addAttribute("districtList", reportService.getAllDistricts());
    
        return "tradedisplay";
    }

    @GetMapping("/AboutStrive")
    public String aboutStrive() {
        return "/aboutstrive";
    }

    @GetMapping("/DisclosureManagement")
    public String disclosureManagement() {
        return "/disclosuremanagement";
    }

}
