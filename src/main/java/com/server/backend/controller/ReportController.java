package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.DistrictCollegeType;
import com.server.backend.DTO.Reports.distcode;
import com.server.backend.DTO.Reports.distnames;
import com.server.backend.Repository.districtmasterrepo;

@RestController
public class ReportController {

        private final districtmasterrepo districtmasterrepo;
        public ReportController(districtmasterrepo districtmasterrepo) {
            this.districtmasterrepo = districtmasterrepo;
        }

        @GetMapping("/districts")
        public distnames tradeDisplayPost() {
            List<String> districtNames = districtmasterrepo.findAllNames();
            distnames names = new distnames();
            names.setDistrictName(districtNames);
            return names;
        }


        @PostMapping("/districts/code")
        public distcode tradeDisplayPost(@RequestBody DistrictCollegeType districtCollegeType) {
           String dist = districtCollegeType.getDist();
           //String type = districtCollegeType.getType();

            List<String> dist_code = districtmasterrepo.findCodeByDistrictName(dist);
            distcode dt_code = new distcode();
            dt_code.setDistrictCode(dist_code);
            
            return dt_code ;

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