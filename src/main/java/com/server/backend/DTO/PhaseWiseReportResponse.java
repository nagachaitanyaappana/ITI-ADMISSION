package com.server.backend.DTO;

import lombok.Data;

@Data
public class PhaseWiseReportResponse {
    private String distName;
    private int phaseI;
    private int phaseII;
    private int phaseIII;
    private int phaseIV;
    private int phaseV;
    private int total;
    private int today;

    public PhaseWiseReportResponse(String distName, int phaseI, int phaseII, int phaseIII, int phaseIV,
            int phaseV, int total, int today) {
        this.distName = distName;
        this.phaseI = phaseI;
        this.phaseII = phaseII;
        this.phaseIII = phaseIII;
        this.phaseIV = phaseIV;
        this.phaseV = phaseV;
        this.total = total;
        this.today = today;
    }
}