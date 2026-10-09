package com.server.backend.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class ItiTradeId implements Serializable {
    private String itiCode;
    private Integer tradecode;

    public ItiTradeId() {
    }

    public ItiTradeId(String itiCode, Integer tradecode) {
        this.itiCode = itiCode;
        this.tradecode = tradecode;
    }
}