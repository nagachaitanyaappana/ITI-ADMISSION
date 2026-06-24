package com.server.backend.DTO.Reports;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantCountResponse {
    private List<DistCountRow> govtPvt;
    private List<DistCountRow> govt;
    private List<DistCountRow> pvt;
    private int govtPvtTotal;
    private int govtTotal;
    private int pvtTotal;

    @Data
    @AllArgsConstructor
    public static class DistCountRow {
        private String distName;
        private int count;
    }
}