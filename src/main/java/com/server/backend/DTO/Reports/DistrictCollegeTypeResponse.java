// DTO for TradeDisplay report. This class will be used to receive the request body (District and Type) from the frontend.
package com.server.backend.DTO.Reports;

import lombok.Data;

@Data
public class DistrictCollegeTypeResponse {
    private String dist;
    private String type;
}
