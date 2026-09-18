package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "shift_unit_permitted", schema = "public")
@Getter
@Setter
@IdClass(ShiftUnitPermittedId.class)
public class ShiftUnitPermitted{
    
    @Id
    @Column(name = "iti_code", nullable = false, length = 4)
    private String itiCode;

    @Id
    @Column(name = "trade_code", nullable = false, length = 50)
    private String tradeCode;

    @Column(name = "strength")
    private Integer strength;

    @Id
    @Column(name = "shift_allowed", length = 1)
    private String shiftAllowed;

    @Id
    @Column(name = "unit_allowed", length = 2)
    private String unitAllowed;

    @Column(name="available_year")
    private Boolean availableYear;
    //
    @Column(name = "strength_vacant")
    private Integer strengthVacant;
}