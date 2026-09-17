package com.server.backend.DTO.AdmissionTimingsDTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateEntryRequest(
    String minqul,
    String reservation,
    @JsonAlias({"date", "calDate", "cal_date", "caldate"}) String calDate,
    @JsonAlias({"time", "calTime", "cal_time", "caltime"}) String calTime
) {}
