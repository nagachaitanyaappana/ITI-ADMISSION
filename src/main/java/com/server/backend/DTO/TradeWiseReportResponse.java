package com.server.backend.DTO;

import lombok.Data;

@Data
public class TradeWiseReportResponse {
    private String tradeName;
    private String tradeCode;
    private int totalStrength;
    private int filled;
    private int vacant;

    public TradeWiseReportResponse(String tradeName, String tradeCode, int totalStrength,
            int filled, int vacant) {
        this.tradeName = tradeName;
        this.tradeCode = tradeCode;
        this.totalStrength = totalStrength;
        this.filled = filled;
        this.vacant = vacant;
    }
}