package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "ranks")
@IdClass(MeritListId.class)
public class MeritList{

    @Column(name = "dist_code")
    private String dist_Code;

    @Id
    @Column(name = "reg_id")
    private String reg_id;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "iti_code")
    private String iti_Code;

    @Id
    @Column(name = "qual")
    private String qual;

    @Id
    @Column(name = "temp_pk")
    private String temp_pk;

    @Id
    @Column(name = "phase")
    private String phase;

    @Column(name = "trno")
    private String trno;

    @Column(name = "year")
    private Integer year;

    @Column(name = "app_status")
    private String app_status;

    public MeritList() {
    }

    public String getDistCode() {
        return dist_Code;
    }

    public void setDistCode(String dist_Code) {
        this.dist_Code = dist_Code;
    }

    public String getRegId() {
        return reg_id;
    }

    public void setRegId(String reg_id) {
        this.reg_id = reg_id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getItiCode() {
        return iti_Code;
    }

    public void setItiCode(String iti_Code) {
        this.iti_Code = iti_Code;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getTempPk() {
        return temp_pk;
    }

    public void setTempPk(String temp_pk) {
        this.temp_pk = temp_pk;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAppStatus() {
        return app_status;
    }

    public void setAppStatus(String app_status) {
        this.app_status = app_status;
    }
}