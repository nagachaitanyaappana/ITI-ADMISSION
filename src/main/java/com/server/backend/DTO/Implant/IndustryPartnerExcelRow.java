package com.server.backend.DTO.Implant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IndustryPartnerExcelRow {

    private String distName;
    private String itiName;
    private String itiCode;
    private String revisedLeadSector;
    private String proposedNewTrade;
    private String revisedLeadIndustryPartner;
}