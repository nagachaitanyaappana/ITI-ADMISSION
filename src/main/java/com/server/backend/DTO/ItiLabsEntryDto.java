
package com.server.backend.DTO;


import java.util.List;

public class ItiLabsEntryDto {

    private String itiCode;
    private String industryName;
    private String tradeShort;
    private String description;
    private List<LabItemDTO> items;

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

    public List<LabItemDTO> getItems() {
        return items;
    }

    public void setItems(List<LabItemDTO> items) {
        this.items = items;
    }

    public static class LabItemDTO {

        private String itemName;
        private Double itemCost;
        private byte[] itemPhoto;

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

}
