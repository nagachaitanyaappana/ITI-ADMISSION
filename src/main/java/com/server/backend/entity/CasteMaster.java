package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "caste_master", schema = "hrms")
public class CasteMaster {

    @Id
    @Column(name = "caste_id")
    private Long casteId;

    @Column(name = "caste_category")
    private String casteCategory;

    public CasteMaster() {
    }

    public CasteMaster(Long casteId, String casteCategory) {
        this.casteId = casteId;
        this.casteCategory = casteCategory;
    }

    public Long getCasteId() {
        return casteId;
    }

    public void setCasteId(Long casteId) {
        this.casteId = casteId;
    }

    public String getCasteCategory() {
        return casteCategory;
    }

    public void setCasteCategory(String casteCategory) {
        this.casteCategory = casteCategory;
    }
}
