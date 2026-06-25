package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "shift_unit_permitted", schema = "public")
@Data
public class ShiftUnitPermitted {

    @Id
    @Column(name = "iti_code", nullable = false, length = 4)
    private String itiCode;

    @Column(name = "trade_code", nullable = false)
    private Integer tradeCode;

    @Column(name = "strength")
    private Integer strength;

    @Column(name = "shift_allowed", length = 1)
    private String shiftAllowed;

    @Column(name = "unit_allowed", length = 1)
    private String unitAllowed;
}