package com.server.backend.controller.Reports;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "reports", description = "Reports UI pages and documentation endpoints")
@Controller
public class ReportsController {

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
