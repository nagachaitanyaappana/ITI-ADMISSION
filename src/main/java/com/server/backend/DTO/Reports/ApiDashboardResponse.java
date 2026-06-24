package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiDashboardResponse {
    private String distName;
    private int total;
    private int success;
    private int verified;
    private int toBeVerified;
    private int toBeUpdated;
    private int phoneDuplicateRecords;
    private int aadharDuplicateRecords;
    private int emailDuplicateRecords;
}