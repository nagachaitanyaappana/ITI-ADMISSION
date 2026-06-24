package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeWiseReportResponse {
    private String tradeName;
    private String tradeCode;
    private int totalStrength;
    private int filled;
    private int vacant;
}