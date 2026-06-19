package com.server.backend.DTO.Reports;

import lombok.Data;

@Data
public class DistrictOptionResponse {

    private String code;
    private String name;

    public DistrictOptionResponse(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
}
