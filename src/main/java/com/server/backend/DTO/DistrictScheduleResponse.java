package com.server.backend.DTO;

import lombok.Data;

@Data
public class DistrictScheduleResponse {
    private String distName;
    private String itiName;
    private String tradeName;
    private Integer meritFrom;
    private Integer meritTo;
    private String calDate;
    private String calTime;
    private String phase;

    public DistrictScheduleResponse(String distName, String itiName, String tradeName,
            Integer meritFrom, Integer meritTo, String calDate, String calTime, String phase) {
        this.distName = distName;
        this.itiName = itiName;
        this.tradeName = tradeName;
        this.meritFrom = meritFrom;
        this.meritTo = meritTo;
        this.calDate = calDate;
        this.calTime = calTime;
        this.phase = phase;
    }
}