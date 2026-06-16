package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "checklist")
public class Checklist {

    @Column(name = "dist_code")
    private String dist_code;

    @Id
    @Column(name = "regid")
    private String regid;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "iti_code")
    private String iti_code;

    @Column(name = "qual")
    private String qual;

    @Column(name = "temp_pk")
    private Integer temp_pk;

    @Column(name = "phase")
    private String phase;

    @Column(name = "trno")
    private String trno;

    @Column(name = "app_status")
    private String app_status;

    public Checklist() {
    }

    public String getDistCode() {
        return dist_code;
    }

    public void setDistCode(String dist_code){
        this.dist_code= dist_code;
    }

    public String getRegId() {
        return regid;
    }

    public void setRegId(String regid){
        this.regid = regid;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getItiCode() {
        return iti_code;
    }

    public void setItiCode(String iti_code){
        this.iti_code= iti_code;
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
        return app_status;
    }

    public void setAppStatus(String app_status){
        this.app_status=app_status;
    }
}