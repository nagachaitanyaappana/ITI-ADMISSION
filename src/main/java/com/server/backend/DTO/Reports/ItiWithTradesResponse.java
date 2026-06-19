package com.server.backend.DTO.Reports;

import java.util.List;

import lombok.Data;

@Data
public class ItiWithTradesResponse {
    private String code; // Add this
    private String itiName;
    private List<TradeDetail> trades;
}