package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhaseWiseReportResponse {
    private String distName;
    private int phaseI;
    private int phaseII;
    private int phaseIII;
    private int phaseIV;
    private int phaseV;
    private int total;
    private int today;
}