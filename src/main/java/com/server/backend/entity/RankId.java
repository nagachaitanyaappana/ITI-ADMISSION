package com.server.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class RankId implements Serializable {

    private Integer regid;
    private String qual;
    private String tempPk;
    private String phase;

    public RankId() {
    }

    public RankId(Integer regid, String qual, String tempPk, String phase) {
        this.regid = regid;
        this.qual = qual;
        this.tempPk = tempPk;
        this.phase = phase;
    }

    public Integer getRegid() {
        return regid;
    }

    public void setRegid(Integer regid) {
        this.regid = regid;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getTempPk() {
        return tempPk;
    }

    public void setTempPk(String tempPk) {
        this.tempPk = tempPk;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RankId)) return false;
        RankId rankId = (RankId) o;
        return Objects.equals(regid, rankId.regid)
                && Objects.equals(qual, rankId.qual)
                && Objects.equals(tempPk, rankId.tempPk)
                && Objects.equals(phase, rankId.phase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regid, qual, tempPk, phase);
    }
}