package com.server.backend.controller.Labs;



import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.service.LabsReportService;
import com.server.backend.DTO.LabsReportDTO;

@Tag(name = "Labs")
@RestController
@RequestMapping("/placements")
public class LabsReportController {

    private final LabsReportService labsReportService;

    public LabsReportController(
            LabsReportService labsReportService) {
        this.labsReportService = labsReportService;
    }

    @GetMapping("/labs-report")
    public List<LabsReportDTO> getLabsReport(
            @RequestParam(required = false) String itiCode,
            @RequestParam(required = false) String industryName) {

        return labsReportService.getLabsReport(
                itiCode,
                industryName);
    }

}
