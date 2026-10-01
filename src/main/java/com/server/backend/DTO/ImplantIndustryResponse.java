package com.server.backend.DTO;

import lombok.Data;

@Data
public class ImplantIndustryResponse {

    private Long industryId;
    private String industryName;

    public ImplantIndustryResponse(Long industryId, String industryName) {
        this.industryId = industryId;
        this.industryName = industryName;
    }
} 