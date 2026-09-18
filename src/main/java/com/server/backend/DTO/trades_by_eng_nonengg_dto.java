package com.server.backend.DTO;

public class trades_by_eng_nonengg_dto {

    private String tradeName;

    public trades_by_eng_nonengg_dto() {
    }

    public trades_by_eng_nonengg_dto(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
}