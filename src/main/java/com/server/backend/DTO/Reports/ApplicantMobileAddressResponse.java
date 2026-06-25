package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantMobileAddressResponse {
    private String regId;
    private String name;
    private String fatherName;
    private String mobile;
    private String address;
    private String distName;
}