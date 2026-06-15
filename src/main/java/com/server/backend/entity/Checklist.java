package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "checklist")
public class Checklist {

    @Column(name = "dist_code")
    private String distCode;

    @Id
    @Column(name = "reg_id")
    private String regId;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "qual")
    private String qual;

    @Column(name = "temp_pk")
    private Integer tempPk;

    @Column(name = "phase")
    private String phase;

    @Column(name = "trno")
    private String trno;

    @Column(name = "app_status")
    private String appStatus;

    public Checklist() {
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public Integer getTempPk() {
        return tempPk;
    }

    public void setTempPk(Integer tempPk) {
        this.tempPk = tempPk;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getTrno() {
        return trno;
    }

    public void setTrno(String trno) {
        this.trno = trno;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }
}