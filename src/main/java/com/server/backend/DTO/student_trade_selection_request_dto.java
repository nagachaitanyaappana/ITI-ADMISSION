package com.server.backend.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Request body for the candidate-side ITI selection.
 *
 * <p>This is the write path into {@code student_trade_sel} — the half of the legacy selection
 * flow that was missing from the port. Direct port of the insert in
 * {@code applicationForm.open_Application_InterfaceAction}
 * ({@code src/java/applicationForm/open_Application_InterfaceAction.java:321}), whose form posts
 * three parallel arrays ({@code addrs_state_p[]} = district, {@code addrs_district_p[]} =
 * government/private, {@code addrs_mandal_p[]} = ITI code) which are zipped into one row per
 * selection.
 *
 * <p>The port models that as an explicit {@code choices} list. A single district may appear many
 * times (one row per ITI) because {@code student_trade_sel} is keyed on
 * {@code (iti_code, regid)} — a candidate picks ITIs, not districts.
 *
 * <p>{@code year} and {@code phase} are resolved server-side (legacy read the year from
 * {@code iti_params} code '7' and hard-coded {@code phse="3"}); they are accepted here only as
 * an override for testing against a non-current phase.
 */
public class student_trade_selection_request_dto {

    private Integer regid;
    private String year;
    private String phase;
    private Integer freezee;
    private List<Choice> choices = new ArrayList<>();

    public student_trade_selection_request_dto() {
    }

    public Integer getRegid() {
        return regid;
    }

    public void setRegid(Integer regid) {
        this.regid = regid;
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

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    /** One chosen ITI. {@code distCode} is validated against {@code iti.dist_code} when supplied. */
    public static class Choice {

        private String distCode;
        private String itiCode;

        public Choice() {
        }

        public Choice(String distCode, String itiCode) {
            this.distCode = distCode;
            this.itiCode = itiCode;
        }

        public String getDistCode() {
            return distCode;
        }

        public void setDistCode(String distCode) {
            this.distCode = distCode;
        }

        public String getItiCode() {
            return itiCode;
        }

        public void setItiCode(String itiCode) {
            this.itiCode = itiCode;
        }
    }
}
