package com.server.backend.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class ShiftUnitPermittedId implements Serializable {
    private String itiCode;
    private String tradeCode;
    private String shiftAllowed;
    private String unitAllowed;

    public ShiftUnitPermittedId() {
    }

    public ShiftUnitPermittedId(String itiCode, String tradeCode, String shiftAllowed,
            String unitAllowed) {
        this.itiCode = itiCode;
        this.tradeCode = tradeCode;
        this.shiftAllowed = shiftAllowed;
        this.unitAllowed = unitAllowed;
    }
}