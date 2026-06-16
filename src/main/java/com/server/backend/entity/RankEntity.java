package com.server.backend.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "ranks")
@IdClass(RankId.class)
public class RankEntity {

    @Id
    @Column(name = "regid")
    private String regid;

    @Id
    @Column(name = "qual")
    private String qual;

    @Id
    @Column(name = "temp_pk")
    private Long tempPk;

    @Id
    @Column(name = "phase")
    private Integer phase;

    @Column(name = "dist_code")
    private String distCode;

    @Column(name = "rank", nullable = false)
    private Integer rank;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "trno")
    private Integer trno;

    @Column(name = "year")
    private Integer year;

    @Column(name = "app_status")
    private String appStatus;
}