package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifiedApplicationCountResponse {
    private String districtName;
    private int totalApplications;
    private int approved;
    private int rejected;
    private int unverified;
}