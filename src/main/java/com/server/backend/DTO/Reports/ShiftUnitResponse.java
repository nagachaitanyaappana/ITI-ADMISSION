package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShiftUnitResponse {
    private String itiName;
    private String itiType;
    private String tradeName;
    private int strength;
    private String shift;
    private String unit;
}