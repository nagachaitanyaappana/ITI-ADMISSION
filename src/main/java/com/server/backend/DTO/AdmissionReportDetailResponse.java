package com.server.backend.DTO;

import lombok.Data;

@Data
public class AdmissionReportDetailResponse {
    private String admissionNo;
    private String sscHallTicket;
    private String name;
    private String fatherName;
    private String motherName;
    private String dateOfBirth;
    private String mobileNo;
    private String email;
    private String shift;
    private String unit;
    private String pwdCategory;
    private String economicWeakerSection;
    private String isTraineeDualMode;

    public AdmissionReportDetailResponse(String admissionNo, String sscHallTicket, String name,
            String fatherName, String motherName, String dateOfBirth, String mobileNo, String email,
            String shift, String unit, String pwdCategory, String economicWeakerSection,
            String isTraineeDualMode) {
        this.admissionNo = admissionNo;
        this.sscHallTicket = sscHallTicket;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.dateOfBirth = dateOfBirth;
        this.mobileNo = mobileNo;
        this.email = email;
        this.shift = shift;
        this.unit = unit;
        this.pwdCategory = pwdCategory;
        this.economicWeakerSection = economicWeakerSection;
        this.isTraineeDualMode = isTraineeDualMode;
    }
}
