package com.server.backend.DTO;
public class search_iti_by_name_dto {

    private String itiCode;
    private String itiName;

    public search_iti_by_name_dto() {
    }

    public search_iti_by_name_dto(String itiCode, String itiName) {
        this.itiCode = itiCode;
        this.itiName = itiName;
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
}