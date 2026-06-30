package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeWiseVacantResponse {
    private String tradeCode;
    private String tradeName;
    private int totalStrength;
    private int totalFilled;
    private int totalVacant;
}