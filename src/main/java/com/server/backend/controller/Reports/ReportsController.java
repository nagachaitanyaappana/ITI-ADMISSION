package com.server.backend.controller.Reports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.server.backend.DTO.Reports.ItiTradeDisplayResponse;
import com.server.backend.DTO.Reports.TradeDisplayReportRequest;
import com.server.backend.service.Reports.TradeDisplayReportService;

@Controller
public class ReportsController {

    private final TradeDisplayReportService reportService;

    public ReportsController(TradeDisplayReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping({ "/reports/trade-display", "/tradedisplay" })
    public String tradeDisplay(Model model) {
        model.addAttribute("districtList", reportService.getAllDistricts());
        model.addAttribute("itiList", new ArrayList<>());
        model.addAttribute("selectedDist", "");
        model.addAttribute("selectedType", "");
        model.addAttribute("reportSubmitted", false);
        return "tradedisplay";
    }

    @PostMapping({ "/reports/trade-display", "/trade_display2" })
    public String submitTradeDisplay(@ModelAttribute TradeDisplayReportRequest request, Model model) {

        List<ItiTradeDisplayResponse> results = reportService.getItisWithTradesByDistrict(request);

        if (results == null) {
            results = new ArrayList<>();
        }

        model.addAttribute("itiList", results);
        model.addAttribute("districtList", reportService.getAllDistricts());
        model.addAttribute("selectedDist", request.getDist());
        model.addAttribute("selectedType", request.getType());
        model.addAttribute("reportSubmitted", true);
    
        return "tradedisplay";
    }

    @GetMapping({ "/reports/about-strive", "/aboutstrive", "/AboutStrive" })
    public String aboutStrive() {
        return "aboutstrive";
    }

    @GetMapping({ "/reports/disclosure-management", "/disclosuremanagement", "/DisclosureManagement" })
    public String disclosureManagement() {
        return "disclosuremanagement";
    }

}
