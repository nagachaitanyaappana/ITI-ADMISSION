package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "ranks",schema="public")
@IdClass(RankId.class)
public class RankEntity {

    @Id
    @Column(name = "regid")
    private Integer regid;

    @Id
    @Column(name = "qual")
    private String qual;

    @Id
    @Column(name = "temp_pk")
    private String tempPk;

    @Id
    @Column(name = "phase")
    private String phase;

    @Column(name = "dist_code")
    private String distCode;

    @Column(name = "rank", nullable = false)
    private String rank;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "trno")
    private Long trno;

    @Column(name = "year")
    private String year;

    @Column(name = "app_status")
    private String appStatus;

    public RankEntity() {
    }

    public Integer getRegid() {
        return regid;
    }

    public void setRegid(Integer regid) {
        this.regid = regid;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getTempPk() {
        return tempPk;
    }

    public void setTempPk(String tempPk) {
        this.tempPk = tempPk;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public Long getTrno() {
        return trno;
    }

    public void setTrno(Long trno) {
        this.trno = trno;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }
}