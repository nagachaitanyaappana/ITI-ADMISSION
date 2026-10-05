package com.server.backend.DTO;

import java.time.LocalDate;

public class Application_Reprint_DTO {

    private String registrationNumber;
    private String name;
    private String fatherName;
    private String motherName;
    private String gender;
    private LocalDate dob;
    private String aadhaarNumber;
    private Long phoneNumber;
    private String email;
    private String address;
    private Integer pincode;
    private String caste;
    private String subCaste;
    private String local;
    private Boolean economicWeakerSection;
    private Boolean phc;
    private String pwdCategory;
    private Boolean exservice;
    private String year;
    private Long transactionNumber;
    private String phase;
    private String applicationStatus;

    public Application_Reprint_DTO() {
    }

    public Application_Reprint_DTO(
            String registrationNumber,
            String name,
            String fatherName,
            String motherName,
            String gender,
            LocalDate dob,
            String aadhaarNumber,
            Long phoneNumber,
            String email,
            String address,
            Integer pincode,
            String caste,
            String subCaste,
            String local,
            Boolean economicWeakerSection,
            Boolean phc,
            String pwdCategory,
            Boolean exservice,
            String year,
            Long transactionNumber,
            String phase,
            String applicationStatus) {

        this.registrationNumber = registrationNumber;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.gender = gender;
        this.dob = dob;
        this.aadhaarNumber = aadhaarNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.pincode = pincode;
        this.caste = caste;
        this.subCaste = subCaste;
        this.local = local;
        this.economicWeakerSection = economicWeakerSection;
        this.phc = phc;
        this.pwdCategory = pwdCategory;
        this.exservice = exservice;
        this.year = year;
        this.transactionNumber = transactionNumber;
        this.phase = phase;
        this.applicationStatus = applicationStatus;
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

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getSubCaste() {
        return subCaste;
    }

    public void setSubCaste(String subCaste) {
        this.subCaste = subCaste;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Boolean getEconomicWeakerSection() {
        return economicWeakerSection;
    }

    public void setEconomicWeakerSection(Boolean economicWeakerSection) {
        this.economicWeakerSection = economicWeakerSection;
    }

    public Boolean getPhc() {
        return phc;
    }

    public void setPhc(Boolean phc) {
        this.phc = phc;
    }

    public String getPwdCategory() {
        return pwdCategory;
    }

    public void setPwdCategory(String pwdCategory) {
        this.pwdCategory = pwdCategory;
    }

    public Boolean getExservice() {
        return exservice;
    }

    public void setExservice(Boolean exservice) {
        this.exservice = exservice;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Long transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}