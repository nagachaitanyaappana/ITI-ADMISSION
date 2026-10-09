package com.server.backend.DTO;

import lombok.Data;

@Data
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

    public CasteWiseAdmissionsResponse(String districtCode, String districtName, int bcA,
            int bcB, int bcC, int bcD, int bcE, int ews, int exS, int im, int oc, int ph,
            int scI, int scII, int scIII, int sp, int st) {
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.bcA = bcA;
        this.bcB = bcB;
        this.bcC = bcC;
        this.bcD = bcD;
        this.bcE = bcE;
        this.ews = ews;
        this.exS = exS;
        this.im = im;
        this.oc = oc;
        this.ph = ph;
        this.scI = scI;
        this.scII = scII;
        this.scIII = scIII;
        this.sp = sp;
        this.st = st;
    }
}