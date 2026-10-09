package com.server.backend.DTO;

import lombok.Data;

@Data
public class IndustryPartnerDetailsRequest {

    private String distCode;

    private String itiCode;

    private String revisedLeadSector;

    private String proposedNewTrade;

    private String revisedLeadIndustryPartner;
    private String entryBy;

    public IndustryPartnerDetailsRequest() {
    }

    public IndustryPartnerDetailsRequest(String distCode, String itiCode,
            String revisedLeadSector, String proposedNewTrade,
            String revisedLeadIndustryPartner, String entryBy) {
        this.distCode = distCode;
        this.itiCode = itiCode;
        this.revisedLeadSector = revisedLeadSector;
        this.proposedNewTrade = proposedNewTrade;
        this.revisedLeadIndustryPartner = revisedLeadIndustryPartner;
        this.entryBy = entryBy;
    }
}