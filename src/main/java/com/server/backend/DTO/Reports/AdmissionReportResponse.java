package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdmissionReportResponse {
    private String tradeName;
    private int boys;
    private int girls;
    private int total;
}