package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradeDurationSeatsResponse {
    private String tradeCode;
    private String tradeName;
    private int strength;
    private int fill;
    private int vacant;
    private double fillPercentage;
}