package com.server.backend.DTO;

public class Discharge_Admission_DTO {

    private String admissionNumber;
    private String year;

    public Discharge_Admission_DTO() {
    }

    public Discharge_Admission_DTO(String admissionNumber, String year) {
        this.admissionNumber = admissionNumber;
        this.year = year;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
