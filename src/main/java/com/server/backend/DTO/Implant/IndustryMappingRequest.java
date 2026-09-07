package com.server.backend.DTO.Implant;

public class IndustryMappingRequest {
    private Long industryId;
    private Integer tradeCode;
    private String entryBy;

    public Long getIndustryId() { return industryId; }
    public void setIndustryId(Long industryId) { this.industryId = industryId; }
    public Integer getTradeCode() { return tradeCode; }
    public void setTradeCode(Integer tradeCode) { this.tradeCode = tradeCode; }
    public String getEntryBy() { return entryBy; }
    public void setEntryBy(String entryBy) { this.entryBy = entryBy; }
}