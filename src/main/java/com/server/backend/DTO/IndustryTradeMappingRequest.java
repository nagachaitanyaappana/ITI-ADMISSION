package com.server.backend.DTO;

import lombok.Data;

@Data
public class IndustryTradeMappingRequest {

    private Integer itiCode;

    private Long industryId;

    private Integer tradeCode;

    private String tradeName;

    private String tradeShort;

    public IndustryTradeMappingRequest() {
    }

    public IndustryTradeMappingRequest(Integer itiCode, Long industryId, Integer tradeCode,
            String tradeName, String tradeShort) {
        this.itiCode = itiCode;
        this.industryId = industryId;
        this.tradeCode = tradeCode;
        this.tradeName = tradeName;
        this.tradeShort = tradeShort;
    }
}