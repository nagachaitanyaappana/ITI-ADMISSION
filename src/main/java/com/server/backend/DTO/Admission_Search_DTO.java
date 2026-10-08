package com.server.backend.DTO;


import java.time.LocalDate;

public class Admission_Search_DTO {

    private String admissionNumber;
    private String registrationNumber;
    private String name;
    private String fatherName;
    private Long phoneNumber;
    private String gender;
    private String caste;
    private String reservationCategory;
    private String yearOfAdmission;
    private LocalDate dateOfAdmission;
    private String typeOfAdmission;
    private String currentSem;
    private String districtCode;
    private String itiCode;
    private Integer tradeCode;
    private Integer phase;

    public Admission_Search_DTO() {
    }

    public Admission_Search_DTO(
            String admissionNumber,
            String registrationNumber,
            String name,
            String fatherName,
            Long phoneNumber,
            String gender,
            String caste,
            String reservationCategory,
            String yearOfAdmission,
            LocalDate dateOfAdmission,
            String typeOfAdmission,
            String currentSem,
            String districtCode,
            String itiCode,
            Integer tradeCode,
            Integer phase) {

        this.admissionNumber = admissionNumber;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.fatherName = fatherName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.caste = caste;
        this.reservationCategory = reservationCategory;
        this.yearOfAdmission = yearOfAdmission;
        this.dateOfAdmission = dateOfAdmission;
        this.typeOfAdmission = typeOfAdmission;
        this.currentSem = currentSem;
        this.districtCode = districtCode;
        this.itiCode = itiCode;
        this.tradeCode = tradeCode;
        this.phase = phase;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public LocalDate getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(LocalDate dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public String getTypeOfAdmission() {
        return typeOfAdmission;
    }

    public void setTypeOfAdmission(String typeOfAdmission) {
        this.typeOfAdmission = typeOfAdmission;
    }

    public String getCurrentSem() {
        return currentSem;
    }

    public void setCurrentSem(String currentSem) {
        this.currentSem = currentSem;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public Integer getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(Integer tradeCode) {
        this.tradeCode = tradeCode;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

}
