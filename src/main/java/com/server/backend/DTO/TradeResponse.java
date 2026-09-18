package com.server.backend.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeResponse {

    private Integer tradeCode;
    private String tradeName;
    private String tradeShort;
} 
    

