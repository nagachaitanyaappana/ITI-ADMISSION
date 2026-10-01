package com.server.backend.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftUnitPermittedId implements Serializable {
    private String itiCode;
    private String tradeCode;
    private String shiftAllowed;
    private String unitAllowed;
}