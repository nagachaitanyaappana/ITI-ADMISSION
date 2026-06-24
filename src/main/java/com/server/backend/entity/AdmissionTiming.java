package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.IdClass;
@Entity
@Table(name = "admission_timings")
@Data
@IdClass(AdmissionTimingId.class)
public class AdmissionTiming {

    @Id
    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "minqul", nullable = false)
    private String minqul;

    @Column(name = "merit_from", nullable = false)
    private Integer meritFrom;

    @Column(name = "merit_to", nullable = false)
    private Integer meritTo;

    @Column(name = "cal_date")
    private String calDate;

    @Column(name = "cal_time")
    private String calTime;

    @Column(name = "dist_code")
    private String distCode;

    @Column(name = "caste", nullable = false)
    private String caste;

    @Column(name = "trno", nullable = false)
    private Integer trno;

    @Column(name = "temp_pk", nullable = false)
    private String tempPk;
    
    @Id
    @Column(name = "phase")
    private String phase;

    @Column(name = "year")
    private Integer year;

}