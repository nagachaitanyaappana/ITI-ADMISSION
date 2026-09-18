package com.server.backend.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DscOptionsResponse {
    private List<ItiOption> itis;
    private List<TradeOption> trades;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItiOption {
        @JsonProperty("iti_code")
        private String itiCode;
        @JsonProperty("iti_name")
        private String itiName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TradeOption {
        @JsonProperty("trade_code")
        private String tradeCode;
        @JsonProperty("trade_name")
        private String tradeName;
    }
}
