package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GovtPvtSeatsAbstractResponse {
    private String distCode;
    private String distName;
    private int govtStrength;
    private int govtFill;
    private int govtVacant;
    private int pvtStrength;
    private int pvtFill;
    private int pvtVacant;
    private int totalStrength;
    private int totalFill;
    private int totalVacant;
}