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

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reports Controller", description = "Page and report endpoints for the reports UI")
@Controller
public class ReportsController {

    private final TradeDisplayReportService tradeDisplayReportService;

    public ReportsController(TradeDisplayReportService tradeDisplayReportService) {
        this.tradeDisplayReportService = tradeDisplayReportService;
    }

    @Operation(summary = "Display the trade display report page")
    @GetMapping("/reports/trade-display")
    public String tradeDisplay(Model model) {
        model.addAttribute("districtList", tradeDisplayReportService.getDistrictOptions());
        model.addAttribute("itiList", new ArrayList<>());
        model.addAttribute("selectedDist", "");
        model.addAttribute("selectedType", "");
        model.addAttribute("reportSubmitted", false);
        return "tradedisplay";
    }

    @Hidden
    @GetMapping("/tradedisplay")
    public String tradeDisplayAlias() {
        return "redirect:/reports/trade-display";
    }

    @Operation(summary = "Submit trade display request and render report results")
    @PostMapping("/reports/trade-display")
    public String submitTradeDisplay(@ModelAttribute TradeDisplayReportRequest request, Model model) {

        List<ItiTradeDisplayResponse> results = tradeDisplayReportService.getTradeDisplayReport(request);

        if (results == null) {
            results = new ArrayList<>();
        }

        model.addAttribute("itiList", results);
        model.addAttribute("districtList", tradeDisplayReportService.getDistrictOptions());
        model.addAttribute("selectedDist", request.getDist());
        model.addAttribute("selectedType", request.getType());
        model.addAttribute("reportSubmitted", true);
    
        return "tradedisplay";
    }

    @Hidden
    @PostMapping("/trade_display2")
    public String submitTradeDisplayAlias(@ModelAttribute TradeDisplayReportRequest request, Model model) {
        return submitTradeDisplay(request, model);
    }

    @Operation(summary = "Show the About Strive page")
    @GetMapping("/reports/about-strive")
    public String aboutStrive() {
        return "aboutstrive";
    }

    @Hidden
    @GetMapping({ "/aboutstrive", "/AboutStrive" })
    public String aboutStriveAlias() {
        return "redirect:/reports/about-strive";
    }

    @Operation(summary = "Show the disclosure management page")
    @GetMapping("/reports/disclosure-management")
    public String disclosureManagement() {
        return "disclosuremanagement";
    }

    @Hidden
    @GetMapping({ "/disclosuremanagement", "/DisclosureManagement" })
    public String disclosureManagementAlias() {
        return "redirect:/reports/disclosure-management";
    }

    @GetMapping("/reports/api-docs")
    public String apiDocs() {
        return "swagger";
    }

}
