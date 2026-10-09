package com.server.backend.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItiSeatMatrixId implements Serializable {
    private String iti_code;
    private Integer trade_code;
    private String year;
    private Integer phase;

    public ItiSeatMatrixId() {
    }

    public ItiSeatMatrixId(String iti_code, Integer trade_code, String year, Integer phase) {
        this.iti_code = iti_code;
        this.trade_code = trade_code;
        this.year = year;
        this.phase = phase;
    }
}