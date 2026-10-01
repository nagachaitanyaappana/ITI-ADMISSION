package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistrictWiseApplicationCountResponse {
    private String distName;
    private Integer totalApplications;
    private Integer approved;
    private Integer rejected;
    private Integer unverified;
}