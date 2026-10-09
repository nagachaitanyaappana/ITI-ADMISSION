package com.server.backend.DTO;

import lombok.Data;

@Data
public class DistrictWiseApplicationCountResponse {
    private String distName;
    private Integer totalApplications;
    private Integer approved;
    private Integer rejected;
    private Integer unverified;

    public DistrictWiseApplicationCountResponse(String distName, Integer totalApplications,
            Integer approved, Integer rejected, Integer unverified) {
        this.distName = distName;
        this.totalApplications = totalApplications;
        this.approved = approved;
        this.rejected = rejected;
        this.unverified = unverified;
    }
}