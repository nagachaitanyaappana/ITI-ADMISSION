package com.server.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.DisplayCollegeTrade;
import com.server.backend.DTO.Reports.DistrictCollegeType;

@RestController
public class ReportController {

        @PostMapping("/tradeDisplay")
        public List<DisplayCollegeTrade> tradeDisplayPost(@RequestBody DistrictCollegeType districtCollegeType) {

        List<DisplayCollegeTrade> trades = new ArrayList<>();

        DisplayCollegeTrade trade1 = new DisplayCollegeTrade();
        trade1.setCollegeName("College A");
        trade1.setTradeName("Electrician");
        trade1.setSeatStrength("100");

        DisplayCollegeTrade trade2 = new DisplayCollegeTrade();
        trade2.setCollegeName("College B");
        trade2.setTradeName("Fitter");
        trade2.setSeatStrength("80");

        trades.add(trade1);
        trades.add(trade2);

        return trades;
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