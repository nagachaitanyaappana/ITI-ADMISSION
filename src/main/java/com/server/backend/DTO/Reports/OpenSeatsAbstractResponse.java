package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenSeatsAbstractResponse {
    private String distCode;
    private String distName;
    private int noOfSeats;
    private int fill;
    private int vacant;
}