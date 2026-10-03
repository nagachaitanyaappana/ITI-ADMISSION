package com.server.backend.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "admission_timings", schema = "public")
@IdClass(AdmissionTimingId.class)
public class AdmissionTiming {

    @Id
    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "minqul", nullable = false)
    private String minqul;

    @Column(name = "merit_from", nullable = false)
    private Integer meritFrom;

    @Column(name = "merit_to", nullable = false)
    private Integer meritTo;

    @Column(name = "cal_date")
    private LocalDate calDate;

    @Column(name = "cal_time")
    private LocalTime calTime;

    @Column(name = "dist_code")
    private String distCode;

    @Column(name = "caste", nullable = false)
    private String caste;

    @Column(name = "trno", nullable = false)
    private Long trno;

    @Column(name = "temp_pk", nullable = false)
    private String tempPk;

    @Id
    @Column(name = "phase")
    private String phase;

    @Column(name = "year")
    private String year;

    // Getters and Setters

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getMinqul() {
        return minqul;
    }

    public void setMinqul(String minqul) {
        this.minqul = minqul;
    }

    public Integer getMeritFrom() {
        return meritFrom;
    }

    public void setMeritFrom(Integer meritFrom) {
        this.meritFrom = meritFrom;
    }

    public Integer getMeritTo() {
        return meritTo;
    }

    public void setMeritTo(Integer meritTo) {
        this.meritTo = meritTo;
    }

    public LocalDate getCalDate() {
        return calDate;
    }

    public void setCalDate(LocalDate calDate) {
        this.calDate = calDate;
    }

    public LocalTime getCalTime() {
        return calTime;
    }

    public void setCalTime(LocalTime calTime) {
        this.calTime = calTime;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public Long getTrno() {
        return trno;
    }

    public void setTrno(Long trno) {
        this.trno = trno;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}