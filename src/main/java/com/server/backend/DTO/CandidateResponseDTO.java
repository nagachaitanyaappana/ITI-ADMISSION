package com.server.backend.DTO;

public class CandidateResponseDTO {

    private String regid;
    private Integer rank;
    private String qual;
    private String dist_code;
    private String iti_code;
    private Integer phase;
    private Integer year;
    private String app_status;

    // Default Constructor
    public CandidateResponseDTO() {
    }

    // Parameterized Constructor
    public CandidateResponseDTO(String regid,
                                Integer rank,
                                String qual,
                                String dist_code,
                                String iti_code,
                                Integer phase,
                                Integer year,
                                String app_status) {

        this.regid = regid;
        this.rank = rank;
        this.qual = qual;
        this.dist_code = dist_code;
        this.iti_code = iti_code;
        this.phase = phase;
        this.year = year;
        this.app_status = app_status;
    }

    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getIti_code() {
        return iti_code;
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getApp_status() {
        return app_status;
    }

    public void setApp_status(String app_status) {
        this.app_status = app_status;
    }

    @Override
    public String toString() {
        return "CandidateResponseDTO{" +
                "regid='" + regid + '\'' +
                ", rank=" + rank +
                ", qual='" + qual + '\'' +
                ", dist_code='" + dist_code + '\'' +
                ", iti_code='" + iti_code + '\'' +
                ", phase=" + phase +
                ", year=" + year +
                ", app_status='" + app_status + '\'' +
                '}';
    }
}