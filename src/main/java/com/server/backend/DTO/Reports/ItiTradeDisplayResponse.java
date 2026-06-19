package com.server.backend.DTO.Reports;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ItiTradeDisplayResponse {
    private String code;
    private String itiName;
    private List<TradeDetail> trades;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TradeDetail {
        private String tradeName;
        private int strength;
    }
}
