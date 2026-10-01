package com.server.backend.DTO;


public class eligible_iti_list_dto {

    private String itiCode;
    private String itiName;
    private String tradeShort;
    private Integer strengthVacant;

    public eligible_iti_list_dto() {
    }

    public eligible_iti_list_dto(String itiCode, String itiName,
            String tradeShort, Integer strengthVacant) {
        this.itiCode = itiCode;
        this.itiName = itiName;
        this.tradeShort = tradeShort;
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

    public Integer getStrengthVacant() {
        return strengthVacant;
    }

    public void setStrengthVacant(Integer strengthVacant) {
        this.strengthVacant = strengthVacant;
    }
}