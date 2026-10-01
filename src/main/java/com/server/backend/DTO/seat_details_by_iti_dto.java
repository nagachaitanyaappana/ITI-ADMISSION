package com.server.backend.DTO;



public class seat_details_by_iti_dto {

    private String tradeShort;
    private Integer strength;
    private Integer strengthFill;
    private Integer strengthVacant;

    public seat_details_by_iti_dto() {
    }

    public seat_details_by_iti_dto(String tradeShort, Integer strength,
            Integer strengthFill, Integer strengthVacant) {
        this.tradeShort = tradeShort;
        this.strength = strength;
        this.strengthFill = strengthFill;
        this.strengthVacant = strengthVacant;
    }

    public String getTradeShort() {
        return tradeShort;
    }

    public void setTradeShort(String tradeShort) {
        this.tradeShort = tradeShort;
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