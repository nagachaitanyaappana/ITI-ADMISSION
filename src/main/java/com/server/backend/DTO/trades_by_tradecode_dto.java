package com.server.backend.DTO;

public class trades_by_tradecode_dto {

    private Integer tradeCode;
    private String tradeName;
    private Integer durationYrs;
    private String minQual;

    public trades_by_tradecode_dto(Integer tradeCode, String tradeName,
                             Integer durationYrs, String minQual) {
        this.tradeCode = tradeCode;
        this.tradeName = tradeName;
        this.durationYrs = durationYrs;
        this.minQual = minQual;
    }

    public Integer getTradeCode() {
        return tradeCode;
    }

    public String getTradeName() {
        return tradeName;
    }

    public Integer getDurationYrs() {
        return durationYrs;
    }

    public String getMinQual() {
        return minQual;
    }
}
