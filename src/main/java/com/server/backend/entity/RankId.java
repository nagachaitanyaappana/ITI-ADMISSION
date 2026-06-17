package com.server.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class RankId implements Serializable {

    private String regid;
    private String qual;
    private Long tempPk;
    private String phase;

    public RankId() {
    }

    public RankId(String regid, String qual, Long tempPk, String phase) {
        this.regid = regid;
        this.qual = qual;
        this.tempPk = tempPk;
        this.phase = phase;
    }

    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public Long getTempPk() {
        return tempPk;
    }

    public void setTempPk(Long tempPk) {
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