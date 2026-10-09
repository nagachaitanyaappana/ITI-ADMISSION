package com.server.backend.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DscOptionsResponse {
    private List<ItiOption> itis;
    private List<TradeOption> trades;

    public DscOptionsResponse(List<ItiOption> itis, List<TradeOption> trades) {
        this.itis = itis;
        this.trades = trades;
    }

    @Data
    @NoArgsConstructor
    public static class ItiOption {
        @JsonProperty("iti_code")
        private String itiCode;
        @JsonProperty("iti_name")
        private String itiName;

        public ItiOption(String itiCode, String itiName) {
            this.itiCode = itiCode;
            this.itiName = itiName;
        }
    }

    @Data
    @NoArgsConstructor
    public static class TradeOption {
        @JsonProperty("trade_code")
        private String tradeCode;
        @JsonProperty("trade_name")
        private String tradeName;

        public TradeOption(String tradeCode, String tradeName) {
            this.tradeCode = tradeCode;
            this.tradeName = tradeName;
        }
    }
}
