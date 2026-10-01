package com.server.backend.DTO;

/**
 * Request body for recording a candidate's trade selection.
 *
 * <p>Mirrors the legacy {@code Tradesel_entry_Form}: a candidate picks an ITI (or, for a
 * district-scoped run, a district) and that choice is written to {@code trade_sel}. The
 * {@code phase} and {@code year} are resolved server-side from {@code iti_params} code '7' and
 * {@code admissions.admission_phase}, exactly as {@code Tradesel_entry_Action} does.
 */
public class TradeSelectionRequestDto {

    private Integer regid;
    private String itiCode;
    private String distCode;

    /** Optional override. When 1 the row is frozen immediately (dev/test convenience). */
    private Integer freezee;

    public TradeSelectionRequestDto() {
    }

    public TradeSelectionRequestDto(Integer regid, String itiCode, String distCode, Integer freezee) {
        this.regid = regid;
        this.itiCode = itiCode;
        this.distCode = distCode;
        this.freezee = freezee;
    }

    public Integer getRegid() {
        return regid;
    }

    public void setRegid(Integer regid) {
        this.regid = regid;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public Integer getFreezee() {
        return freezee;
    }

    public void setFreezee(Integer freezee) {
        this.freezee = freezee;
    }
}