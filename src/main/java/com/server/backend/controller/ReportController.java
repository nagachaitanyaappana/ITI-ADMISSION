package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.DistrictCollegeType;
import com.server.backend.Repository.districtmasterrepo;

@RestController
public class ReportController {

        private final districtmasterrepo districtmasterrepo;
        public ReportController(districtmasterrepo districtmasterrepo) {
            this.districtmasterrepo = districtmasterrepo;
        }

        @PostMapping("/tradeDisplay")
        public List<String> tradeDisplayPost(@RequestBody DistrictCollegeType districtCollegeType) {
           // String dist = districtCollegeType.getDist();
            //String type = districtCollegeType.getType();

            
            return districtmasterrepo.findAllNames();

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