package com.server.backend.DTO;

import lombok.Data;

@Data
public class ItiListResponse {

    private String districtName;
    private String nicItiCode;
    private String ncvtCode;
    private String itiName;

    public ItiListResponse(String districtName, String nicItiCode, String ncvtCode, String itiName) {
        this.districtName = districtName;
        this.nicItiCode = nicItiCode;
        this.ncvtCode = ncvtCode;
        this.itiName = itiName;
    }
}
