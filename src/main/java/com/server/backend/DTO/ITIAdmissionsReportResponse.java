package com.server.backend.DTO;

import lombok.Data;

@Data
public class ITIAdmissionsReportResponse {
    private String admissionNumber;
    private String name;
    private String sscRegno;
    private String yearOfAdmission;

    public ITIAdmissionsReportResponse(String admissionNumber, String name, String sscRegno,
            String yearOfAdmission) {
        this.admissionNumber = admissionNumber;
        this.name = name;
        this.sscRegno = sscRegno;
        this.yearOfAdmission = yearOfAdmission;
    }
}