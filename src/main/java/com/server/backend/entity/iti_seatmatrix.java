package com.server.backend.entity;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLHStoreType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "iti_seatmatrix" , schema = "public")
@Data
public class iti_seatmatrix {

    @Column(name = "iti_code" , nullable = false)
    private String iti_code;

    @Column(name = "trade_code" , nullable = false)
    private Integer trade_code;

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "strength", columnDefinition = "hstore" , nullable = false)
    private Map<String, String> strength = new HashMap<>();

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "strength_fill", columnDefinition = "hstore" , nullable = false)
    private Map<String, String> strength_fill = new HashMap<>();

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "strength_vacant", columnDefinition = "hstore" , nullable = false)
    private Map<String, String> strength_vacant = new HashMap<>();

    @Column(name = "year" , nullable = false)
    private String year;

    @Column(name = "phase" , nullable = false)
    private Integer phase;

}