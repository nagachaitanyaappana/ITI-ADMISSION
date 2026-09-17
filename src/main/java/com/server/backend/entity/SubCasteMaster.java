package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subcaste_master", schema = "hrms")
public class SubCasteMaster {

    @Id
    @Column(name = "subcaste_id")
    private Long subcasteId;

    @Column(name = "sub_caste")
    private String subCaste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caste_id")
    private CasteMaster casteMaster;

    public SubCasteMaster() {
    }

    public SubCasteMaster(Long subcasteId, String subCaste, CasteMaster casteMaster) {
        this.subcasteId = subcasteId;
        this.subCaste = subCaste;
        this.casteMaster = casteMaster;
    }

    public Long getSubcasteId() {
        return subcasteId;
    }

    public void setSubcasteId(Long subcasteId) {
        this.subcasteId = subcasteId;
    }

    public String getSubCaste() {
        return subCaste;
    }

    public void setSubCaste(String subCaste) {
        this.subCaste = subCaste;
    }

    public CasteMaster getCasteMaster() {
        return casteMaster;
    }

    public void setCasteMaster(CasteMaster casteMaster) {
        this.casteMaster = casteMaster;
    }
}