package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StateDashboardResponse {
    private String districtName;
    private String distCode;
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
