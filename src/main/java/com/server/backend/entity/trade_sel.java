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
@Table(name = "trade_sel", schema = "public")
@IdClass(TradeSelId.class)
@Data
public class trade_sel {

    @Id
    @Column(name = "regid" , nullable = false)
    private String regid;

    @Id
    @Column(name = "iti_code" , nullable = false , length = 4)
    private String iti_code;

    @Column(name = "dist_code" , length = 2)
    private String dist_code;

    @Column(name = "temp" , nullable = false)
    private Integer temp;

    @Column(name = "freezee")
    private Integer freezee;

    @Column(name = "temp_code" , nullable = false , length = 4)
    private String temp_code;

    @Column(name = "trno")
    private Long trno;

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "phase", columnDefinition = "hstore")
    private Map<String, String> phase = new HashMap<>();

    @Column(name = "year" , length = 4)
    private String year;

    @Column(name = "dist_name" , length = 25)
    private String dist_name;

    @Column(name = "new_value" , length = 1)
    private String new_value;

    @Column(name = "rec_status" , length = 1)
    private String rec_status;

}