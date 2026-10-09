package com.server.backend.DTO;

import lombok.Data;

@Data
public class OpenSeatsAbstractResponse {
    private String distCode;
    private String distName;
    private int noOfSeats;
    private int fill;
    private int vacant;

    public OpenSeatsAbstractResponse(String distCode, String distName, int noOfSeats, int fill, int vacant) {
        this.distCode = distCode;
        this.distName = distName;
        this.noOfSeats = noOfSeats;
        this.fill = fill;
        this.vacant = vacant;
    }
}