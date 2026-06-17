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
    private String regid;

    @Id
    @Column(name = "qual")
    private String qual;

    @Id
    @Column(name = "temp_pk")
    private Long tempPk;

    @Id
    @Column(name = "phase")
    private Integer phase;

    @Column(name = "dist_code")
    private String distCode;

    @Column(name = "rank", nullable = false)
    private Integer rank;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "trno")
    private Integer trno;

    @Column(name = "year")
    private Integer year;

    @Column(name = "app_status")
    private String appStatus;

    public RankEntity() {
    }

    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public Long getTempPk() {
        return tempPk;
    }

    public void setTempPk(Long tempPk) {
        this.tempPk = tempPk;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
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

    public Integer getTrno() {
        return trno;
    }

    public void setTrno(Integer trno) {
        this.trno = trno;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }
}