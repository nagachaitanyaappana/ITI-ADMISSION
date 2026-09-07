package com.server.backend.DTO.Implant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}