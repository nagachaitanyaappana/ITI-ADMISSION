package com.server.backend.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItiSeatMatrixId implements Serializable {
    private String iti_code;
    private Integer trade_code;
    private String year;
    private Integer phase;
}