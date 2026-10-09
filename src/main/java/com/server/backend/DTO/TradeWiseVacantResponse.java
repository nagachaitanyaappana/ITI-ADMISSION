package com.server.backend.DTO;

import lombok.Data;

@Data
public class TradeWiseVacantResponse {
    private String tradeCode;
    private String tradeName;
    private int totalStrength;
    private int totalFilled;
    private int totalVacant;

    public TradeWiseVacantResponse(String tradeCode, String tradeName, int totalStrength,
            int totalFilled, int totalVacant) {
        this.tradeCode = tradeCode;
        this.tradeName = tradeName;
        this.totalStrength = totalStrength;
        this.totalFilled = totalFilled;
        this.totalVacant = totalVacant;
    }
}