package com.server.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class AdmissionPhaseId implements Serializable {

    private String year;
    private Integer phase;
    private String itiType;

    public AdmissionPhaseId() {
    }

    public AdmissionPhaseId(String year, Integer phase, String itiType) {
        this.year = year;
        this.phase = phase;
        this.itiType = itiType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public String getItiType() {
        return itiType;
    }

    public void setItiType(String itiType) {
        this.itiType = itiType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdmissionPhaseId)) return false;
        AdmissionPhaseId that = (AdmissionPhaseId) o;
        return Objects.equals(year, that.year)
                && Objects.equals(phase, that.phase)
                && Objects.equals(itiType, that.itiType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, phase, itiType);
    }
}