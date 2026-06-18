package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ranks",schema="public")
@Data
@IdClass(MeritListId.class)
public class MeritList{

    @Column(name = "dist_code")
    private String dist_Code;

    @Id
    @Column(name = "regid")
    private Integer regid;

    @Column(name = "rank")
    private String rank;

    @Column(name = "iti_code")
    private String iti_Code;

    @Id
    @Column(name = "qual")
    private String qual;

    @Id
    @Column(name = "temp_pk")
    private String temp_pk;

    @Id
    @Column(name = "phase")
    private String phase;

    @Column(name = "trno")
    private Long trno;

    @Column(name = "year")
    private String year;

    @Column(name = "app_status")
    private String app_status;

  }