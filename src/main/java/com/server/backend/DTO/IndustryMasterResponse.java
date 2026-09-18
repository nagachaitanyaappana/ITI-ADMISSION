package com.server.backend.DTO;

public class IndustryMasterResponse {

    private Long industryId;
    private String industryName;
    private String industryType;
    private String industryAddress;

    public IndustryMasterResponse() {
    }

    public IndustryMasterResponse(
            Long industryId,
            String industryName,
            String industryType,
            String industryAddress) {

        this.industryId = industryId;
        this.industryName = industryName;
        this.industryType = industryType;
        this.industryAddress = industryAddress;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getIndustryAddress() {
        return industryAddress;
    }

    public void setIndustryAddress(String industryAddress) {
        this.industryAddress = industryAddress;
    }
}