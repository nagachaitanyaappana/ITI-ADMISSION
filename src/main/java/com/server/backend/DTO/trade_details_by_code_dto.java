package com.server.backend.DTO;



public class trade_details_by_code_dto {

    private String tradeShort;
    private String tradeName;
    private String minQual;

    public trade_details_by_code_dto() {
    }

    public trade_details_by_code_dto(String tradeShort, String tradeName, String minQual) {
        this.tradeShort = tradeShort;
        this.tradeName = tradeName;
        this.minQual = minQual;
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

    public String getMinQual() {
        return minQual;
    }

    public void setMinQual(String minQual) {
        this.minQual = minQual;
    }
}