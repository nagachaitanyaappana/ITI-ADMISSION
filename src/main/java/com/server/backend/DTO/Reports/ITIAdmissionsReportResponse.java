package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ITIAdmissionsReportResponse {
    private String admissionNumber;
    private String name;
    private String sscRegno;
    private String yearOfAdmission;
}