package com.server.backend.DTO;

import lombok.Data;

@Data
public class ApplicantMobileAddressResponse {
    private String sscRegno;
    private String mobile;
    private String regId;
    private String name;
    private String fatherName;
    private String motherName;
    private String address;

    public ApplicantMobileAddressResponse(String sscRegno, String mobile, String regId,
            String name, String fatherName, String motherName, String address) {
        this.sscRegno = sscRegno;
        this.mobile = mobile;
        this.regId = regId;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.address = address;
    }
}