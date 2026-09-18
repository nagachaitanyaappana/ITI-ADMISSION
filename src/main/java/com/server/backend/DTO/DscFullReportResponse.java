package com.server.backend.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DscFullReportResponse {
    private Meta meta;
    private ItiInfo iti;
    private TradeInfo trade;
    private List<CategoryGroup> categories;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        @JsonProperty("selection_type")
        private String selectionType;
        private String session;
        private String phase;
        @JsonProperty("dist_code")
        private String distCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItiInfo {
        @JsonProperty("iti_code")
        private String itiCode;
        @JsonProperty("iti_name")
        private String itiName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TradeInfo {
        @JsonProperty("trade_code")
        private String tradeCode;
        @JsonProperty("trade_name")
        private String tradeName;
        @JsonProperty("total_strength")
        private int totalStrength;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryGroup {
        @JsonProperty("category_code")
        private String categoryCode;
        private int strength;
        private int filled;
        private int vacant;
        private List<CandidateRow> candidates;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CandidateRow {
        private int slNo;
        private String rank;
        @JsonProperty("admission_number")
        private String admissionNumber;
        private String name;
        @JsonProperty("father_name")
        private String fatherName;
        private String gender;
        @JsonProperty("date_of_birth")
        private String dateOfBirth;
        private String caste;
    }
}
