package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLHStoreType;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "ititrade")
@IdClass(ItiTradeId.class) // Links the composite primary key class
@Data
public class ititrade {

    @Id
    @Column(name = "iti_code", nullable = false)
    private String itiCode;

    @Id
    @Column(name = "trade_code", nullable = false)
    private Integer tradecode;

    @Column(name = "trade_short")
    private String tradeshort;

    @Column(name = "strength")
    private Integer strength;

    @Column(name = "available_for_year")
    private String availableforyear;

    @Column(name = "trade_freeze")
    private Integer tradefreeze;

    @Column(name = "conv_approval")
    private Integer conv_approval;

    @Column(name = "strength_fill")
    private Integer strength_fill;

    @Column(name = "strength_vacant")
    private Integer strength_vacant;

    @Column(name = "trno")
    private Long trno;

    @Column(name = "no_of_units")
    private String no_of_units;

    // Properly declared Map + attached Hypersistence HStore type translation
    @Type(PostgreSQLHStoreType.class)
    @Column(name = "aff_no", columnDefinition = "hstore")
    private Map<String, String> aff_no = new HashMap<>();

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "aff_year", columnDefinition = "hstore")
    private Map<String, String> aff_year = new HashMap<>();

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "aff_date", columnDefinition = "hstore")
    private Map<String, String> aff_date = new HashMap<>();

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "aff_no_of_units", columnDefinition = "hstore")
    private Map<String, String> aff_no_of_units = new HashMap<>();

    @Column(name = "unit_strength")
    private Integer unit_strength ;

    @Column(name = "mod")
    private Integer mod ;

}