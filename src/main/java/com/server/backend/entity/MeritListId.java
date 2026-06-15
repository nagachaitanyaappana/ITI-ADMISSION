package com.server.backend.entity;
import java.io.Serializable;
import java.util.Objects;
public class MeritListId implements Serializable {
    private String reg_id;
    private String qual;
    private String temp_pk;
    private String phase;
    public MeritListId() {
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeritListId)) return false;

        MeritListId that = (MeritListId) o;

        return Objects.equals(reg_id, that.reg_id)
                && Objects.equals(qual, that.qual)
                && Objects.equals(temp_pk, that.temp_pk)
                && Objects.equals(phase, that.phase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reg_id, qual, temp_pk, phase);
    }
}