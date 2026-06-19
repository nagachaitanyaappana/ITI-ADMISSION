package com.server.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.service.ReportService;
import java.util.List;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;

    }

   @GetMapping("/tradedisplay")
    public String tradedisplay(Model model) {
        model.addAttribute("districtList", reportService.getAllDistricts());
        return "tradedisplay.html"; 
    }

    
    @PostMapping("/trade_display2")
    public String submitTradeDisplay(@ModelAttribute DistrictCollegeTypeResponse request, Model model) {
    
        // Log for debugging to see what is coming in
        System.out.println("Received District: " + request.getDist());
        System.out.println("Received Type: " + request.getType());

        List<ItiWithTradesResponse> results = reportService.getItisWithTradesByDistrict(request);
    
        model.addAttribute("itiList", results);
        model.addAttribute("districtList", reportService.getAllDistricts());

        return "tradedisplay"; 
    }

    @GetMapping("/AboutStrive")
    public String aboutStrive() {
        return "/aboutstrive.html";
    }

    @GetMapping("/DisclosureManagement")
    public String disclosureManagement() {
        return "/disclosuremanagement.html";
    }

}
