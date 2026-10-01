package com.server.backend.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItiTradeDisplayResponse {
    private String code;
    private String itiName;
    private String govt;
    private List<TradeDetail> trades;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TradeDetail {
        private String tradeName;
        private int strength;
    }
}
