package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantMobileAddressResponse {
    private String sscRegno;
    private String mobile;
    private String regId;
    private String name;
    private String fatherName;
    private String motherName;
    private String address;
}