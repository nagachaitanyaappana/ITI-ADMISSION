package com.server.backend.DTO;

import lombok.Data;

@Data
public class CurrentAdmissionPhaseResponse {
    private String year;
    private int phase;

    public CurrentAdmissionPhaseResponse(String year, int phase) {
        this.year = year;
        this.phase = phase;
    }
}
