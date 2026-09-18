package com.server.backend.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ViewScheduleRequest(
    String caste,
    String minqul,
    @JsonAlias({"time", "calTime", "cal_time", "caltime"}) String calTime,
    @JsonAlias({"date", "calDate", "cal_date", "caldate"}) String calDate
) {}
    

