package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StrengthFilledSeatsResponse {
    private String itiCode;
    private String itiName;
    private String distName;
    private int strength;
    private int strengthFill;
    private int vacant;
    private double fillPercentage;
}