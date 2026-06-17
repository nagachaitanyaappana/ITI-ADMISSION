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
    @Column(name = "regid")
    private Integer regid;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "qual")
    private String qual;

    @Column(name = "temp_pk")
    private Integer temp_pk;

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

    public void setDistCode(String distCode){
        this.distCode= distCode;
    }

    public Integer getRegId() {
        return regid;
    }

    public void setRegId(Integer regid){
        this.regid = regid;
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

    public void setItiCode(String iti_code){
        this.itiCode= iti_code;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public Integer getTempPk() {
        return temp_pk;
    }

    public void setTempPk(Integer temp_pk){
        this.temp_pk=temp_pk;
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

    public void setAppStatus(String appStatus){
        this.appStatus=appStatus;
    }
}