package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CollegeWiseOpenSeatsResponse {
    private String itiCode;
    private String itiName;
    private int noOfSeats;
    private int fill;
    private int vacant;
}