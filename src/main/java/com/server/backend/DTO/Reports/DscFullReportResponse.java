package com.server.backend.DTO.Reports;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DscFullReportResponse {
    private String itiName;
    private String tradeName;
    private String session;
    private List<CategoryGroup> categories;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryGroup {
        private String categoryCode;
        private int strength;
        private int filled;
        private int vacant;
        private List<CandidateRow> candidates;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CandidateRow {
        private int slNo;
        private String rank;
        private String admissionNumber;
        private String name;
        private String fatherName;
        private String gender;
        private String dateOfBirth;
        private String caste;
    }
}