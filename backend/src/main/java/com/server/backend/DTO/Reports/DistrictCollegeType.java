// DTO for TradeDisplay report. This class will be used to receive the request body (District and Type) from the frontend.
package com.server.backend.DTO.Reports;

public class DistrictCollegeType {
    private String dist;
    private String type;

    public String getDist() {
        return dist;
    }

    public String getType() {
        return type ;
    }

    public void setDist(String dist) {
        this.dist = dist ;
    }

    public void setType(String type) {
        this.type = type ;
    }
}
