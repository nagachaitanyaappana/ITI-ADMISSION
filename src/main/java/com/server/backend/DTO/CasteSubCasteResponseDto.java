package com.server.backend.DTO;
import java.util.List;

public class CasteSubCasteResponseDto {

    private Long casteId;
    private String casteCategory;
    private List<SubCasteDto> subCastes;

    public CasteSubCasteResponseDto() {
    }

    public CasteSubCasteResponseDto(Long casteId, String casteCategory,
                                    List<SubCasteDto> subCastes) {
        this.casteId = casteId;
        this.casteCategory = casteCategory;
        this.subCastes = subCastes;
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

    public List<SubCasteDto> getSubCastes() {
        return subCastes;
    }

    public void setSubCastes(List<SubCasteDto> subCastes) {
        this.subCastes = subCastes;
    }
}