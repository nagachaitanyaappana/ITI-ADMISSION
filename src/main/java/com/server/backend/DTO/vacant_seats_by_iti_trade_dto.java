package com.server.backend.DTO;

public class vacant_seats_by_iti_trade_dto {
    
    private String itiCode;
    private String tradeShort;
    private String tradeName;
    private Integer strength;
    private Integer strengthFill;
    private Integer strengthVacant;

    public vacant_seats_by_iti_trade_dto() {
    }

    public vacant_seats_by_iti_trade_dto(String itiCode, String tradeShort,
            String tradeName, Integer strength,
            Integer strengthFill, Integer strengthVacant) {
        this.itiCode = itiCode;
        this.tradeShort = tradeShort;
        this.tradeName = tradeName;
        this.strength = strength;
        this.strengthFill = strengthFill;
        this.strengthVacant = strengthVacant;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
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

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getStrengthFill() {
        return strengthFill;
    }

    public void setStrengthFill(Integer strengthFill) {
        this.strengthFill = strengthFill;
    }

    public Integer getStrengthVacant() {
        return strengthVacant;
    }

    public void setStrengthVacant(Integer strengthVacant) {
        this.strengthVacant = strengthVacant;
    }
}