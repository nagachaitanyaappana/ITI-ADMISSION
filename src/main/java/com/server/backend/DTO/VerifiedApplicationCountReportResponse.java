package com.server.backend.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifiedApplicationCountReportResponse {
    private String year;
    @JsonProperty("dist_code")
    private String distCode;
    private List<VerifiedRow> data;

    public VerifiedApplicationCountReportResponse(String year, String distCode, List<VerifiedRow> data) {
        this.year = year;
        this.distCode = distCode;
        this.data = data;
    }

    @Data
    @NoArgsConstructor
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

        public VerifiedRow(String districtName, int totalApplications, int approved, int rejected,
                int unverified) {
            this.districtName = districtName;
            this.totalApplications = totalApplications;
            this.approved = approved;
            this.rejected = rejected;
            this.unverified = unverified;
        }
    }
}
