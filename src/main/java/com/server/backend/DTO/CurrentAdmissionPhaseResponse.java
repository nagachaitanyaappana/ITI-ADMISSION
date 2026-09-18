package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentAdmissionPhaseResponse {
    private String year;
    private int phase;
}
