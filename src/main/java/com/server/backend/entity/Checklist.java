package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "checklist")
@Data
public class Checklist 
{

    @Column(name = "dist_code")
    private String distCode;

    @Id
    @Column(name = "regid")
    private Integer regid;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "qual")
    private String qual;

    @Column(name = "temp_pk")
    private Integer temp_pk;

    @Column(name = "phase")
    private String phase;

    @Column(name = "trno")
    private String trno;

    @Column(name = "app_status")
    private String appStatus;

}