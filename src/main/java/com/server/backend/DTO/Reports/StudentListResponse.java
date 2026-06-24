package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentListResponse {
    private String admissionNo;
    private String tradeCode;
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