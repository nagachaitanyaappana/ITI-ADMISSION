package com.server.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @GetMapping("/AboutStrive")
    public String aboutStrive() {
        return "/aboutstrive.html";
    }

    @GetMapping("/DisclosureManagement")
    public String disclosureManagement() {
        return "/disclosuremanagement.html";
    }

}
