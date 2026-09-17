
package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "labitems")
public class LabItems {

    @Id
    @Column(name = "lab_items_id")
    private Long labItemsId;

    @Column(name = "item_cost")
    private Double itemCost;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_photo")
    private byte[] itemPhoto;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "lab_id")
    private Long labId;

    public LabItems() {
    }

    public Long getLabItemsId() {
        return labItemsId;
    }

    public void setLabItemsId(Long labItemsId) {
        this.labItemsId = labItemsId;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public byte[] getItemPhoto() {
        return itemPhoto;
    }

    public void setItemPhoto(byte[] itemPhoto) {
        this.itemPhoto = itemPhoto;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }
}

