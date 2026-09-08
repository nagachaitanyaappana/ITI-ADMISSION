package com.server.backend.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.server.backend.DTO.LabsReportDTO;
import com.server.backend.service.ItiLabsReportService;
@Tag(name = "ItiLabsReportController", description = "Controller for ITI Labs Report")
@RestController
@RequestMapping("/itilogin")
public class ItiLabsReportController {

    private final ItiLabsReportService itiLabsReportService;

    public ItiLabsReportController(
            ItiLabsReportService itiLabsReportService) {

        this.itiLabsReportService = itiLabsReportService;
    }

    @GetMapping("/labs-report")
    public List<LabsReportDTO> getItiLabsReport(
            @RequestParam String itiCode) {

        return itiLabsReportService.getItiLabsReport(itiCode);
    }

}
