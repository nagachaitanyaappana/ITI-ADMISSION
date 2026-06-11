package com.server.backend.DTO.Reports;

public class DisplayCollegeTrade {
    private String collegeName;
    private String tradeName;
    private String seatStrength;

    public String getCollegeName() {
        return collegeName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public String getSeatStrength() {
        return seatStrength;
    }
    
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public void setSeatStrength(String seatStrength) {
        this.seatStrength = seatStrength;
    }
    
}
