package com.server.backend.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class IndustryPartnerDetailsResponse {

    private Long pid;

    private String distCode;

    private String itiCode;

    private String distName;

    private String itiName;

    private String revisedLeadSector;

    private String proposedNewTrade;

    private String revisedLeadIndustryPartner;

    private String entryBy;

    private Timestamp entryDate;

    public IndustryPartnerDetailsResponse() {
    }

    public IndustryPartnerDetailsResponse(Long pid, String distCode, String itiCode,
            String distName, String itiName, String revisedLeadSector, String proposedNewTrade,
            String revisedLeadIndustryPartner, String entryBy, Timestamp entryDate) {
        this.pid = pid;
        this.distCode = distCode;
        this.itiCode = itiCode;
        this.distName = distName;
        this.itiName = itiName;
        this.revisedLeadSector = revisedLeadSector;
        this.proposedNewTrade = proposedNewTrade;
        this.revisedLeadIndustryPartner = revisedLeadIndustryPartner;
        this.entryBy = entryBy;
        this.entryDate = entryDate;
    }
}