package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CasteWiseAdmissionsResponse {
    private String districtCode;
    private String districtName;
    private int bcA;
    private int bcB;
    private int bcC;
    private int bcD;
    private int bcE;
    private int ews;
    private int exS;
    private int im;
    private int oc;
    private int ph;
    private int scI;
    private int scII;
    private int scIII;
    private int sp;
    private int st;
}