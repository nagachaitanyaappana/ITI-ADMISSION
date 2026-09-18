package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantCountDistrictResponse {
    private String distCode;
    private String distName;
    private int count;
}
