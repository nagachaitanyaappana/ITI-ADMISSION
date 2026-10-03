
package com.server.backend.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule_Entry_DTO {

    private String itiCode;
    private String minqul;
    private Integer meritFrom;
    private Integer meritTo;
    private LocalDate calDate;
    private LocalTime calTime;
    private String distCode;
    private String caste;
    private Long trno;
    private String tempPk;
    private String phase;
    private String year;

    public Schedule_Entry_DTO() {
    }

    public Schedule_Entry_DTO(
            String itiCode,
            String minqul,
            Integer meritFrom,
            Integer meritTo,
            LocalDate calDate,
            LocalTime calTime,
            String distCode,
            String caste,
            Long trno,
            String tempPk,
            String phase,
            String year) {

        this.itiCode = itiCode;
        this.minqul = minqul;
        this.meritFrom = meritFrom;
        this.meritTo = meritTo;
        this.calDate = calDate;
        this.calTime = calTime;
        this.distCode = distCode;
        this.caste = caste;
        this.trno = trno;
        this.tempPk = tempPk;
        this.phase = phase;
        this.year = year;
    }

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