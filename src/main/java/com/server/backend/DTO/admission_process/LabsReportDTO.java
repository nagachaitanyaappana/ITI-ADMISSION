package com.server.backend.DTO.admission_process;

public class LabsReportDTO {

    private Long labId;
    private Long labItemsId;

    private String itiCode;
    private String industryName;
    private String tradeShort;
    private String description;

    private String itemName;
    private Double itemCost;
    private byte[] itemPhoto;

    public LabsReportDTO() {
    }

    public LabsReportDTO(
            Long labId,
            Long labItemsId,
            String itiCode,
            String industryName,
            String tradeShort,
            String description,
            String itemName,
            Double itemCost,
            byte[] itemPhoto) {

        this.labId = labId;
        this.labItemsId = labItemsId;
        this.itiCode = itiCode;
        this.industryName = industryName;
        this.tradeShort = tradeShort;
        this.description = description;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemPhoto = itemPhoto;
    }

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }

    public Long getLabItemsId() {
        return labItemsId;
    }

    public void setLabItemsId(Long labItemsId) {
        this.labItemsId = labItemsId;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getTradeShort() {
        return tradeShort;
    }

    public void setTradeShort(String tradeShort) {
        this.tradeShort = tradeShort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }

    public byte[] getItemPhoto() {
        return itemPhoto;
    }

    public void setItemPhoto(byte[] itemPhoto) {
        this.itemPhoto = itemPhoto;
    }
}

