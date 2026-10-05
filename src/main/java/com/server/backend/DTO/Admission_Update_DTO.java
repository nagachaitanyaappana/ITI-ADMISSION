package com.server.backend.DTO;

import java.time.LocalDate;

public class Admission_Update_DTO {

    private String admissionNumber;
    private String registrationId;
    private String name;
    private String fatherName;
    private String motherName;
    private String itiCode;
    private String tradeCode;
    private String caste;
    private String reservationCategory;
    private String yearOfAdmission;
    private String districtCode;
    private LocalDate dateOfBirth;
    private LocalDate dateOfAdmission;
    private Integer phase;

    public Admission_Update_DTO() {
    }

    public Admission_Update_DTO(
            String admissionNumber,
            String registrationId,
            String name,
            String fatherName,
            String motherName,
            String itiCode,
            String tradeCode,
            String caste,
            String reservationCategory,
            String yearOfAdmission,
            String districtCode,
            LocalDate dateOfBirth,
            LocalDate dateOfAdmission,
            Integer phase) {

        this.admissionNumber = admissionNumber;
        this.registrationId = registrationId;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.itiCode = itiCode;
        this.tradeCode = tradeCode;
        this.caste = caste;
        this.reservationCategory = reservationCategory;
        this.yearOfAdmission = yearOfAdmission;
        this.districtCode = districtCode;
        this.dateOfBirth = dateOfBirth;
        this.dateOfAdmission = dateOfAdmission;
        this.phase = phase;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getReservationCategory() {
        return reservationCategory;
    }

    public void setReservationCategory(String reservationCategory) {
        this.reservationCategory = reservationCategory;
    }

    public String getYearOfAdmission() {
        return yearOfAdmission;
    }

    public void setYearOfAdmission(String yearOfAdmission) {
        this.yearOfAdmission = yearOfAdmission;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(LocalDate dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

}
