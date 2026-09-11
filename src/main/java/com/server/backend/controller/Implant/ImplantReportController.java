  package com.server.backend.controller.Implant;

import com.server.backend.DTO.Industries.ImplantIndustryResponse;
import com.server.backend.service.Implant.ImplantReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/implant-report")
public class ImplantReportController {

    private final ImplantReportService service;

    public ImplantReportController(ImplantReportService service) {
        this.service = service;
    }

    @GetMapping("/industries")
    public ResponseEntity<List<ImplantIndustryResponse>> getIndustries(
            @RequestParam Integer itiCode) {

        return ResponseEntity.ok(
                service.getIndustries(itiCode)
        );
    }

    @GetMapping("/report")
public ResponseEntity<?> getReport(
        @RequestParam Integer industryId) {

    return ResponseEntity.ok(
            service.getImplantReportByIndustry(industryId));
}

@GetMapping("/download-excel")
public ResponseEntity<byte[]> downloadExcel(
        @RequestParam Integer industryId) {

    byte[] excel = service.downloadExcel(industryId);

    return ResponseEntity.ok()
            .header(
                    "Content-Disposition",
                    "attachment; filename=inplant-report.xlsx")
            .header(
                    "Content-Type",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            .body(excel);
}
}