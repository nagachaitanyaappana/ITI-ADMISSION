package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodayScheduleResponse {
    private String distName;
    private String itiName;
    private Integer meritFrom;
    private Integer meritTo;
    private String calDate;
    private String calTime;
}