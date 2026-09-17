package com.server.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "admission_phase", schema = "admissions")
@IdClass(AdmissionPhaseId.class)
public class AdmissionPhase {

    @Id
    @Column(name = "year")
    private String year;

    @Id
    @Column(name = "phase")
    private Integer phase;

    @Id
    @Column(name = "iti_type")
    private String itiType;

    @Column(name = "fromdate")
    private LocalDate fromDate;

    @Column(name = "todate")
    private LocalDate toDate;

    @Column(name = "seatmatrix_phase")
    private Integer seatmatrixPhase;

    @Column(name = "admission_role")
    private String admissionRole;

    @Column(name = "lid")
    private Integer lid;

    @Column(name = "current")
    private Boolean current;

    @Column(name = "trno")
    private Long trno;

    @Column(name = "application_from_date")
    private LocalDateTime applicationFromDate;

    @Column(name = "application_to_date")
    private LocalDateTime applicationToDate;

    @Column(name = "verification_start_date")
    private LocalDateTime verificationStartDate;

    @Column(name = "verification_end_date")
    private LocalDateTime verificationEndDate;

    @Column(name = "meritliststartdate")
    private LocalDateTime meritListStartDate;

    @Column(name = "meritlisttodate")
    private LocalDateTime meritListToDate;

    @Column(name = "admissions_start_date")
    private LocalDateTime admissionsStartDate;

    @Column(name = "admissions_end_date")
    private LocalDateTime admissionsEndDate;

    @Column(name = "reverificationstartdate")
    private LocalDateTime reverificationStartDate;

    @Column(name = "reverificationenddate")
    private LocalDateTime reverificationEndDate;

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

    // Generate Constructors, Getters and Setters
}