package com.server.backend.DTO.admission_process;



public class available_trades_by_iti_dto {

    private String tradeShort;
    private String tradeName;
    private Integer strength;
    private Integer strengthFill;
    private Integer strengthVacant;
    private Integer unitStrength;

    public available_trades_by_iti_dto() {
    }

    public available_trades_by_iti_dto(String tradeShort, String tradeName,
                                       Integer strength, Integer strengthFill,
                                       Integer strengthVacant, Integer unitStrength) {
        this.tradeShort = tradeShort;
        this.tradeName = tradeName;
        this.strength = strength;
        this.strengthFill = strengthFill;
        this.strengthVacant = strengthVacant;
        this.unitStrength = unitStrength;
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

    public Integer getUnitStrength() {
        return unitStrength;
    }

    public void setUnitStrength(Integer unitStrength) {
        this.unitStrength = unitStrength;
    }
}