package com.server.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class MeritListId implements Serializable {

    public Integer regid;
    public String qual;
    public String tempPk;
    public String phase;

    public MeritListId() {
    }

    public MeritListId(Integer regid, String qual, String tempPk, String phase) {
        this.regid = regid;
        this.qual = qual;
        this.tempPk = tempPk;
        this.phase = phase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeritListId)) return false;

        MeritListId that = (MeritListId) o;

        return Objects.equals(regid, that.regid)
                && Objects.equals(qual, that.qual)
                && Objects.equals(tempPk, that.tempPk)
                && Objects.equals(phase, that.phase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regid, qual, tempPk, phase);
    }
}