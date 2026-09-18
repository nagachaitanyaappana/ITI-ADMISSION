package com.server.backend.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifiedApplicationCountReportResponse {
    private String year;
    @JsonProperty("dist_code")
    private String distCode;
    private List<VerifiedRow> data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifiedRow {
        @JsonProperty("District Name")
        private String districtName;
        @JsonProperty("Total Applications")
        private int totalApplications;
        @JsonProperty("Approved")
        private int approved;
        @JsonProperty("Rejected")
        private int rejected;
        @JsonProperty("Unverified")
        private int unverified;
    }
}
