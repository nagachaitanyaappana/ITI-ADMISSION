package com.server.backend.entity;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "checklist" , schema = "public")
@Data
public class Checklist 
{

    @Column(name = "dist_code")
    private String distCode;

    @Id
    @Column(name = "regid")
    private Integer regid;

    @Column(name = "rank")
    private String rank;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "qual")
    private String qual;

    @Column(name = "temp_pk")
    private String tempPk;

    @Column(name = "phase")
    private String phase;

    @Column(name = "trno")
    private BigInteger trno;

    @Column(name = "app_status")
    private String appStatus;

}






















