package com.server.backend.DTO;

import lombok.Data;

@Data
public class ShiftUnitResponse {
    private String itiName;
    private String itiType;
    private String tradeName;
    private int strength;
    private String shift;
    private String unit;

    public ShiftUnitResponse(String itiName, String itiType, String tradeName, int strength, String shift,
            String unit) {
        this.itiName = itiName;
        this.itiType = itiType;
        this.tradeName = tradeName;
        this.strength = strength;
        this.shift = shift;
        this.unit = unit;
    }
}