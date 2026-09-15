package com.server.backend.controller.Implant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.server.backend.DTO.Industries.IndustryConnectedTradesDTO;
import com.server.backend.service.Implant.IndustryConnectedTradesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/industry-connected-trades")
public class IndustryConnectedTradesController {

    private final IndustryConnectedTradesService service;

    public IndustryConnectedTradesController(
            IndustryConnectedTradesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<IndustryConnectedTradesDTO>> getReport() {

        return ResponseEntity.ok(service.getReport());
    }

    @GetMapping("/download-excel")
public ResponseEntity<byte[]> downloadExcel() {

    byte[] excelData = service.downloadExcel();

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=IndustryConnectedTradesReport.xlsx"
            )
            .contentType(
                    MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    )
            )
            .body(excelData);
}
}