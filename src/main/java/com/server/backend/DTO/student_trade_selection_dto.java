package com.server.backend.DTO;

/**
 * One saved row of {@code student_trade_sel}, resolved with the district and ITI names the
 * selection page needs.
 *
 * <p>Read-only projection: {@code student_trade_sel} stores codes only, so the district name comes
 * from {@code dist_mst} and the ITI name / {@code govt} flag from {@code iti} — the same joins the
 * legacy edit views used ({@code web/getIti_list.jsp:72}).
 */
public class student_trade_selection_dto {

    private Integer regid;
    private String distCode;
    private String distName;
    private String itiCode;
    private String itiName;

    /** {@code iti.govt}: 'G' for government, 'P' for private. */
    private String govt;

    private String tempCode;
    private Long trno;
    private String year;

    /** Raw hstore text, e.g. {@code "3"=>"true"}. */
    private String phase;

    private Integer freezee;

    public student_trade_selection_dto() {
    }

    public student_trade_selection_dto(Integer regid, String distCode, String distName,
            String itiCode, String itiName, String govt, String tempCode, Long trno,
            String year, String phase, Integer freezee) {
        this.regid = regid;
        this.distCode = distCode;
        this.distName = distName;
        this.itiCode = itiCode;
        this.itiName = itiName;
        this.govt = govt;
        this.tempCode = tempCode;
        this.trno = trno;
        this.year = year;
        this.phase = phase;
        this.freezee = freezee;
    }

    public Integer getRegid() {
        return regid;
    }

    public void setRegid(Integer regid) {
        this.regid = regid;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getItiName() {
        return itiName;
    }

    public void setItiName(String itiName) {
        this.itiName = itiName;
    }

    public String getGovt() {
        return govt;
    }

    public void setGovt(String govt) {
        this.govt = govt;
    }

    public String getTempCode() {
        return tempCode;
    }

    public void setTempCode(String tempCode) {
        this.tempCode = tempCode;
    }

    public Long getTrno() {
        return trno;
    }

    public void setTrno(Long trno) {
        this.trno = trno;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Integer getFreezee() {
        return freezee;
    }

    public void setFreezee(Integer freezee) {
        this.freezee = freezee;
    }
}
