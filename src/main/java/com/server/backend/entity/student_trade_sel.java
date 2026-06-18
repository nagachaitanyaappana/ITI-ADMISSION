package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Map;
import java.util.HashMap;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLHStoreType;


@Entity
@Table(name = "student_trade_sel" , schema = "public")
@IdClass(StudentTradeSelId.class)
@Data
public class student_trade_sel {

    @Id
    @Column(name = "regid", nullable = false)
    private Integer regid;

    @Id
    @Column(name = "iti_code", nullable = false , length = 4)
    private String itiCode;

    @Column(name = "dist_code" , length = 2)
    private String dist_code;

    @Column(name = "temp", nullable = false, insertable = false, updatable = false)
    private Integer temp;

    @Column(name = "freezee")
    private Integer freezee ;

    @Column(name = "temp_code" , nullable = false)
    private String temp_code;

    @Column(name = "trno")
    private Long trno;

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "phase", columnDefinition = "hstore")
    private Map<String, String> phase = new HashMap<>();

    @Column(name = "year" , length = 4)
    private String year;


}