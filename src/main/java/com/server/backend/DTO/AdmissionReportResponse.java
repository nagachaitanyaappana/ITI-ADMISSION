package com.server.backend.DTO;

import lombok.Data;

@Data
public class AdmissionReportResponse {
    private String tradeName;
    private int boys;
    private int girls;
    private int total;

    public AdmissionReportResponse(String tradeName, int boys, int girls, int total) {
        this.tradeName = tradeName;
        this.boys = boys;
        this.girls = girls;
        this.total = total;
    }
}
