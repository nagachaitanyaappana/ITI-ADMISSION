
    package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trade_sel2025phase1")
public class TradeSel2025Phase1 {

    @Id
    @Column(name = "regid")
    private Integer regid;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "dist_code")
    private String distCode;

    @Column(name = "temp_code")
    private String tempCode;

    @Column(name = "phase")
    private String phase;

    @Column(name = "year")
    private String year;

    @Column(name = "freeze")
    private Integer freeze;

    public TradeSel2025Phase1() {
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

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getTempCode() {
        return tempCode;
    }

    public void setTempCode(String tempCode) {
        this.tempCode = tempCode;
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

