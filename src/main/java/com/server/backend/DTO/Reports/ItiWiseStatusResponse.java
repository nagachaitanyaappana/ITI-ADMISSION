package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItiWiseStatusResponse {
    private String distName;
    private String itiName;
    private String itiCode;
    private int total;
    private int success;
    private int pendingSid;
    private int verified;
    private int toBeVerified;
    private int toBeUpdated;
    private int phoneDuplicateRecords;
    private int aadharDuplicateRecords;
    private int emailDuplicateRecords;
}