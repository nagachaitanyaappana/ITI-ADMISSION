package com.server.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.server.backend.service.ReportService;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;

    }

    @GetMapping("/tradedisplay")
    public String tradedisplay() {
        return "tradedisplay.html" + reportService.getDistricts();
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
