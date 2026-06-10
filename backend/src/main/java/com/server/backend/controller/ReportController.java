package com.server.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.DistrictCollegeType;

@RestController
public class ReportController{
     private String dist ;
     private String type ;
    @PostMapping("/tradeDisplay")
    public String tradeDisplayPost(@RequestBody DistrictCollegeType districtCollegeType) {
        dist = districtCollegeType.getDist();
        type = districtCollegeType.getType();

        return "Received District: " + dist + ", Type: " + type;
    }

    @GetMapping("/AboutStrive")
    public String aboutStrive() {
        return "About Strive.";
    }

    @GetMapping("/DisclosureManagement")
    public String disclosureManagement() {
        return "Disclosure Management.";//Testing Git
    }
}
