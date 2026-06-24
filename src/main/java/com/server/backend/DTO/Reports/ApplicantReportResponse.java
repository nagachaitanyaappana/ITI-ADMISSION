package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantReportResponse {
    private String sscRegno;
    private String mobileNo;
    private String regId;
    private String name;
    private String fatherName;
    private String motherName;
}