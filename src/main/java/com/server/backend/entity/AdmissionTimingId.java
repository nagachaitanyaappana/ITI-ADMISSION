package com.server.backend.entity;
import java.io.Serializable;
import java.util.Objects;
public class AdmissionTimingId implements Serializable {

    private String itiCode;
    private String phase;

    public AdmissionTimingId() {
    }

    public AdmissionTimingId(String itiCode, String phase) {
        this.itiCode = itiCode;
        this.phase = phase;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if(!(obj instanceof AdmissionTimingId))
            return false;
        AdmissionTimingId that = (AdmissionTimingId) obj;
        return Objects.equals(this.itiCode, that.itiCode) && Objects.equals(this.phase, that.phase);
    }
    @Override
    public int hashCode() {
        return Objects.hash(itiCode, phase);
    }
}