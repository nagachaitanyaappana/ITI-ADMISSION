package com.server.backend.DTO;

import java.time.LocalDate;

public class Dsc_List_DTO {

    private Integer slNo;
    private Integer rank;
    private String admissionNumber;
    private String name;
    private String fatherName;
    private String gender;
    private LocalDate dateOfBirth;
    private String caste;

    public Dsc_List_DTO() {
    }

    public Dsc_List_DTO(
            Integer slNo,
            Integer rank,
            String admissionNumber,
            String name,
            String fatherName,
            String gender,
            LocalDate dateOfBirth,
            String caste) {

        this.slNo = slNo;
        this.rank = rank;
        this.admissionNumber = admissionNumber;
        this.name = name;
        this.fatherName = fatherName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.caste = caste;
    }

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

}
