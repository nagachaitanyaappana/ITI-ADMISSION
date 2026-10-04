package com.server.backend.DTO;

public class Call_Letter_DTO {
    

    private String rank;
    private String caste;
    private Integer regid;
    private String itiCode;
    private String qualification;
    private String tempPk;
    private String phase;
    private Long trno;
    private String year;
    private String name;

    public Call_Letter_DTO() {
    }

    public Call_Letter_DTO(
            String rank,
            String caste,
            Integer regid,
            String itiCode,
            String qualification,
            String tempPk,
            String phase,
            Long trno,
            String year,
            String name) {

        this.rank = rank;
        this.caste = caste;
        this.regid = regid;
        this.itiCode = itiCode;
        this.qualification = qualification;
        this.tempPk = tempPk;
        this.phase = phase;
        this.trno = trno;
        this.year = year;
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
