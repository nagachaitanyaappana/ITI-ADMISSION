package com.server.backend.DTO;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItiTradeDisplayResponse {
    private String code;
    private String itiName;
    private String govt;
    private List<TradeDetail> trades;

    public ItiTradeDisplayResponse(String code, String itiName, String govt, List<TradeDetail> trades) {
        this.code = code;
        this.itiName = itiName;
        this.govt = govt;
        this.trades = trades;
    }

    @Data
    @NoArgsConstructor
    public static class TradeDetail {
        private String tradeName;
        private int strength;

        public TradeDetail(String tradeName, int strength) {
            this.tradeName = tradeName;
            this.strength = strength;
        }
    }
}
