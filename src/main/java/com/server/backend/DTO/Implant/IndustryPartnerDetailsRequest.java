package com.server.backend.DTO.Implant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustryPartnerDetailsRequest {

    private String distCode;

    private String itiCode;

    private String revisedLeadSector;

    private String proposedNewTrade;

    private String revisedLeadIndustryPartner;
    private String entryBy;
}