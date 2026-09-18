package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
}
