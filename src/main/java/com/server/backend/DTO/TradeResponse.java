package com.server.backend.DTO;
import lombok.Data;

@Data
public class TradeResponse {

    private Integer tradeCode;
    private String tradeName;
    private String tradeShort;

    public TradeResponse(Integer tradeCode, String tradeName, String tradeShort) {
        this.tradeCode = tradeCode;
        this.tradeName = tradeName;
        this.tradeShort = tradeShort;
    }
} 
    

