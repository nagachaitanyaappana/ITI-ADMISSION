package com.server.backend.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    public static class Meta {
        @JsonProperty("selection_type")
        private String selectionType;
        private String session;
        private String phase;
        @JsonProperty("dist_code")
        private String distCode;

        public Meta(String selectionType, String session, String phase, String distCode) {
            this.selectionType = selectionType;
            this.session = session;
            this.phase = phase;
            this.distCode = distCode;
        }
    }

    @Data
    @NoArgsConstructor
    public static class ItiInfo {
        @JsonProperty("iti_code")
        private String itiCode;
        @JsonProperty("iti_name")
        private String itiName;

        public ItiInfo(String itiCode, String itiName) {
            this.itiCode = itiCode;
            this.itiName = itiName;
        }
    }

    @Data
    @NoArgsConstructor
    public static class TradeInfo {
        @JsonProperty("trade_code")
        private String tradeCode;
        @JsonProperty("trade_name")
        private String tradeName;
        @JsonProperty("total_strength")
        private int totalStrength;

        public TradeInfo(String tradeCode, String tradeName, int totalStrength) {
            this.tradeCode = tradeCode;
            this.tradeName = tradeName;
            this.totalStrength = totalStrength;
        }
    }

    @Data
    @NoArgsConstructor
    public static class CategoryGroup {
        @JsonProperty("category_code")
        private String categoryCode;
        private int strength;
        private int filled;
        private int vacant;
        private List<CandidateRow> candidates;

        public CategoryGroup(String categoryCode, int strength, int filled, int vacant,
                List<CandidateRow> candidates) {
            this.categoryCode = categoryCode;
            this.strength = strength;
            this.filled = filled;
            this.vacant = vacant;
            this.candidates = candidates;
        }
    }

    @Data
    @NoArgsConstructor
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

        public CandidateRow(int slNo, String rank, String admissionNumber, String name, String fatherName,
                String gender, String dateOfBirth, String caste) {
            this.slNo = slNo;
            this.rank = rank;
            this.admissionNumber = admissionNumber;
            this.name = name;
            this.fatherName = fatherName;
            this.gender = gender;
            this.dateOfBirth = dateOfBirth;
            this.caste = caste;
        }
    }
}
