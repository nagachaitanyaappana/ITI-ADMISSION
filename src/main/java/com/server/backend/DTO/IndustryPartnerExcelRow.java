package com.server.backend.DTO;

import lombok.Data;

@Data
public class IndustryPartnerExcelRow {

    private String distName;
    private String itiName;
    private String itiCode;
    private String revisedLeadSector;
    private String proposedNewTrade;
    private String revisedLeadIndustryPartner;

    public IndustryPartnerExcelRow(String distName, String itiName, String itiCode,
            String revisedLeadSector, String proposedNewTrade,
            String revisedLeadIndustryPartner) {
        this.distName = distName;
        this.itiName = itiName;
        this.itiCode = itiCode;
        this.revisedLeadSector = revisedLeadSector;
        this.proposedNewTrade = proposedNewTrade;
        this.revisedLeadIndustryPartner = revisedLeadIndustryPartner;
    }
}