package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ititrade")
@Data
public class ititrade {

    @Id
    @Column(name = "iti_code" , nullable = false)
    private String itiCode;

    @Id
    @Column(name = "trade_code" , nullable = false)
    private int tradecode;

    @Column(name = "trade_short")
    private String tradeshort;

    @Column(name = "strength")
    private int strength;

}