package com.server.backend.DTO;


public class vacant_trades_by_district_dto {

    private String itiCode;
    private String itiName;
    private String tradeShort;
    private String tradeName;
    private Integer strengthVacant;

    public vacant_trades_by_district_dto() {
    }

    public vacant_trades_by_district_dto(String itiCode, String itiName,
            String tradeShort, String tradeName, Integer strengthVacant) {
        this.itiCode = itiCode;
        this.itiName = itiName;
        this.tradeShort = tradeShort;
        this.tradeName = tradeName;
        this.strengthVacant = strengthVacant;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getItiName() {
        return itiName;
    }

    public void setItiName(String itiName) {
        this.itiName = itiName;
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

    public Integer getStrengthVacant() {
        return strengthVacant;
    }

    public void setStrengthVacant(Integer strengthVacant) {
        this.strengthVacant = strengthVacant;
    }
}