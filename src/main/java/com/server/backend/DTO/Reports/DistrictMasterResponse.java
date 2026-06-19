package com.server.backend.DTO.Reports;

import lombok.Data;

@Data
public class DistrictMasterResponse {

    private String code;
    private String name;

    public DistrictMasterResponse(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
}
