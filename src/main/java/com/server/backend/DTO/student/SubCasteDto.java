package com.server.backend.DTO.student;
public class SubCasteDto {

    private Long subcasteId;
    private String subCaste;

    public SubCasteDto() {
    }

    public SubCasteDto(Long subcasteId, String subCaste) {
        this.subcasteId = subcasteId;
        this.subCaste = subCaste;
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
}