package com.server.backend.DTO.admission_process;


public class iti_list_by_district_dto {

    private String itiCode;
    private String itiName;
    private String govt;

    public iti_list_by_district_dto() {
    }

    public iti_list_by_district_dto(String itiCode, String itiName, String govt) {
        this.itiCode = itiCode;
        this.itiName = itiName;
        this.govt = govt;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getItiName() {
        return itiName;
    }

    public void setItiName(String itiName) {
        this.itiName = itiName;
    }

    public String getGovt() {
        return govt;
    }

    public void setGovt(String govt) {
        this.govt = govt;
    }
}