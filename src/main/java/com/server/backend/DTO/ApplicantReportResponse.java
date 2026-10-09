package com.server.backend.DTO;

import lombok.Data;

@Data
public class ApplicantReportResponse {
    private String sscRegno;
    private String mobileNo;
    private String regId;
    private String name;
    private String fatherName;
    private String motherName;

    public ApplicantReportResponse(String sscRegno, String mobileNo, String regId,
            String name, String fatherName, String motherName) {
        this.sscRegno = sscRegno;
        this.mobileNo = mobileNo;
        this.regId = regId;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
    }
}