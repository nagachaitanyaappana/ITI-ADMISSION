package com.server.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.DisplayCollegeTrade;
import com.server.backend.DTO.Reports.DistrictCollegeType;

@RestController
public class ReportController {

    @PostMapping("/tradeDisplay")
    public DisplayCollegeTrade tradeDisplayPost(@RequestBody DistrictCollegeType districtCollegeType) {

        DisplayCollegeTrade displayCollegeTrade = new DisplayCollegeTrade();

        displayCollegeTrade.setCollegeName("Example College");
        displayCollegeTrade.setTradeName("Example Trade");
        displayCollegeTrade.setSeatStrength("100");

        return displayCollegeTrade;
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