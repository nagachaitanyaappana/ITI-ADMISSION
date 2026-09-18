package com.server.backend.DTO;
import jakarta.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateTimingsRequest(
    String minqul,
    String reservation,
    @NotNull(message="meritFrom is Required")
    @Min(value=1, message="meritFrom must be greater than or equal to 1")
    Integer meritFrom,
    @NotNull(message="meritTo is Required")
    Integer meritTo,
    @JsonAlias({"date", "calDate", "cal_date", "caldate"}) String calDate,
    @JsonAlias({"time", "calTime", "cal_time", "caltime"}) String calTime
) {}
