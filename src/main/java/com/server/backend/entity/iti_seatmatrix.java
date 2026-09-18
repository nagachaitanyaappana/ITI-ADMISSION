package com.server.backend.entity;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLHStoreType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "iti_seatmatrix" , schema = "public")
@IdClass(ItiSeatMatrixId.class)
@Data
public class iti_seatmatrix {

    @Id    
    @Column(name = "iti_code" , nullable = false , length = 4)
    private String iti_code;

    @Id
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

    @Id
    @Column(name = "year" , nullable = false , length = 4)
    private String year;

    @Id
    @Column(name = "phase" , nullable = false)
    private Integer phase;

}