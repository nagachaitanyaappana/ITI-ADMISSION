package com.server.backend.DTO;
public class candidate_selected_trade_dto {

    private Integer regid;
    private String itiCode;
    private String tempCode;
    private String distCode;
    private String phase;
    private String year;
    private Integer freeze;

    public candidate_selected_trade_dto() {
    }

    public candidate_selected_trade_dto(Integer regid, String itiCode,
            String tempCode, String distCode,
            String phase, String year, Integer freeze) {

        this.regid = regid;
        this.itiCode = itiCode;
        this.tempCode = tempCode;
        this.distCode = distCode;
        this.phase = phase;
        this.year = year;
        this.freeze = freeze;
    }

    public Integer getRegid() {
        return regid;
    }

    public void setRegid(Integer regid) {
        this.regid = regid;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getTempCode() {
        return tempCode;
    }

    public void setTempCode(String tempCode) {
        this.tempCode = tempCode;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getFreeze() {
        return freeze;
    }

    public void setFreeze(Integer freeze) {
        this.freeze = freeze;
    }
}
    

