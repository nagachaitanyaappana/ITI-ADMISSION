package com.server.backend.DTO;

import lombok.Data;

@Data
public class ApplicantCountDistrictResponse {
    private String distCode;
    private String distName;
    private int count;

    public ApplicantCountDistrictResponse(String distCode, String distName, int count) {
        this.distCode = distCode;
        this.distName = distName;
        this.count = count;
    }
}
