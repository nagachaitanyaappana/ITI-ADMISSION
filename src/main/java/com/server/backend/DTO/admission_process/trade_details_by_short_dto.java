package com.server.backend.DTO.admission_process;

public class trade_details_by_short_dto {

    private String tradeShort;
    private String tradeName;
    private Integer durationYrs;
    private String minQual;
    private String engNonEngg;
    private Integer tradeCode;
    private Integer unitStrength;
    private String typeAdmission;

    public trade_details_by_short_dto() {
    }

    public trade_details_by_short_dto(String tradeShort, String tradeName,
            Integer durationYrs, String minQual, String engNonEngg,
            Integer tradeCode, Integer unitStrength, String typeAdmission) {

        this.tradeShort = tradeShort;
        this.tradeName = tradeName;
        this.durationYrs = durationYrs;
        this.minQual = minQual;
        this.engNonEngg = engNonEngg;
        this.tradeCode = tradeCode;
        this.unitStrength = unitStrength;
        this.typeAdmission = typeAdmission;
    }

    public String getTradeShort() {
        return tradeShort;
    }

    public void setTradeShort(String tradeShort) {
        this.tradeShort = tradeShort;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public Integer getDurationYrs() {
        return durationYrs;
    }

    public void setDurationYrs(Integer durationYrs) {
        this.durationYrs = durationYrs;
    }

    public String getMinQual() {
        return minQual;
    }

    public void setMinQual(String minQual) {
        this.minQual = minQual;
    }

    public String getEngNonEngg() {
        return engNonEngg;
    }

    public void setEngNonEngg(String engNonEngg) {
        this.engNonEngg = engNonEngg;
    }

    public Integer getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(Integer tradeCode) {
        this.tradeCode = tradeCode;
    }

    public Integer getUnitStrength() {
        return unitStrength;
    }

    public void setUnitStrength(Integer unitStrength) {
        this.unitStrength = unitStrength;
    }

    public String getTypeAdmission() {
        return typeAdmission;
    }

    public void setTypeAdmission(String typeAdmission) {
        this.typeAdmission = typeAdmission;
    }
}