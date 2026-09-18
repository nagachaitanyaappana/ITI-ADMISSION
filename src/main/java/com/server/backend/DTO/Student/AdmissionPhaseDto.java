package com.server.backend.DTO.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdmissionPhaseDto {

    private String year;
    private Integer phase;
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public Integer getPhase() {
        return phase;
    }
    public void setPhase(Integer phase) {
        this.phase = phase;
    }
    public String getItiType() {
        return itiType;
    }
    public void setItiType(String itiType) {
        this.itiType = itiType;
    }
    public LocalDate getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    public LocalDate getToDate() {
        return toDate;
    }
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
    public Integer getSeatmatrixPhase() {
        return seatmatrixPhase;
    }
    public void setSeatmatrixPhase(Integer seatmatrixPhase) {
        this.seatmatrixPhase = seatmatrixPhase;
    }
    public String getAdmissionRole() {
        return admissionRole;
    }
    public void setAdmissionRole(String admissionRole) {
        this.admissionRole = admissionRole;
    }
    public Integer getLid() {
        return lid;
    }
    public void setLid(Integer lid) {
        this.lid = lid;
    }
    public Boolean getCurrent() {
        return current;
    }
    public void setCurrent(Boolean current) {
        this.current = current;
    }
    public Long getTrno() {
        return trno;
    }
    public void setTrno(Long trno) {
        this.trno = trno;
    }
    public LocalDateTime getApplicationFromDate() {
        return applicationFromDate;
    }
    public void setApplicationFromDate(LocalDateTime applicationFromDate) {
        this.applicationFromDate = applicationFromDate;
    }
    public LocalDateTime getApplicationToDate() {
        return applicationToDate;
    }
    public void setApplicationToDate(LocalDateTime applicationToDate) {
        this.applicationToDate = applicationToDate;
    }
    public LocalDateTime getVerificationStartDate() {
        return verificationStartDate;
    }
    public void setVerificationStartDate(LocalDateTime verificationStartDate) {
        this.verificationStartDate = verificationStartDate;
    }
    public LocalDateTime getVerificationEndDate() {
        return verificationEndDate;
    }
    public void setVerificationEndDate(LocalDateTime verificationEndDate) {
        this.verificationEndDate = verificationEndDate;
    }
    public LocalDateTime getMeritListStartDate() {
        return meritListStartDate;
    }
    public void setMeritListStartDate(LocalDateTime meritListStartDate) {
        this.meritListStartDate = meritListStartDate;
    }
    public LocalDateTime getMeritListToDate() {
        return meritListToDate;
    }
    public void setMeritListToDate(LocalDateTime meritListToDate) {
        this.meritListToDate = meritListToDate;
    }
    public LocalDateTime getAdmissionsStartDate() {
        return admissionsStartDate;
    }
    public void setAdmissionsStartDate(LocalDateTime admissionsStartDate) {
        this.admissionsStartDate = admissionsStartDate;
    }
    public LocalDateTime getAdmissionsEndDate() {
        return admissionsEndDate;
    }
    public void setAdmissionsEndDate(LocalDateTime admissionsEndDate) {
        this.admissionsEndDate = admissionsEndDate;
    }
    public LocalDateTime getReverificationStartDate() {
        return reverificationStartDate;
    }
    public void setReverificationStartDate(LocalDateTime reverificationStartDate) {
        this.reverificationStartDate = reverificationStartDate;
    }
    public LocalDateTime getReverificationEndDate() {
        return reverificationEndDate;
    }
    public void setReverificationEndDate(LocalDateTime reverificationEndDate) {
        this.reverificationEndDate = reverificationEndDate;
    }
    private String itiType;

    private LocalDate fromDate;
    private LocalDate toDate;

    private Integer seatmatrixPhase;
    private String admissionRole;
    private Integer lid;
    private Boolean current;
    private Long trno;

    private LocalDateTime applicationFromDate;
    private LocalDateTime applicationToDate;

    private LocalDateTime verificationStartDate;
    private LocalDateTime verificationEndDate;

    private LocalDateTime meritListStartDate;
    private LocalDateTime meritListToDate;

    private LocalDateTime admissionsStartDate;
    private LocalDateTime admissionsEndDate;

    private LocalDateTime reverificationStartDate;
    private LocalDateTime reverificationEndDate;

    // Generate Constructors
    // Generate Getters & Setters
}