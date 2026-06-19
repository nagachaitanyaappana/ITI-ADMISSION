package com.server.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; 

import com.server.backend.service.ReportService;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;

    }

   @GetMapping("/tradedisplay")
    public String tradedisplay(Model model) {
        // Pass the list of districts to the HTML
        model.addAttribute("districtList", reportService.getAllDistricts());
        return "tradedisplay.html"; 
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
