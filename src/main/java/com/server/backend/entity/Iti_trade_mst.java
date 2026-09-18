package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ititrade_master", schema = "public")
@Data


public class Iti_trade_mst {

    @Id
    @Column(name="trade_short")
    private String tradeShort;
    
    @Column(name="trade_name")
    private  String tradeName;

    @Column(name="durationyrs")
    private Integer durationYrs;

    @Column(name="eng_nonengg")
    private String eng_Nonengg;

    @Column(name="min_qual")
    private String minQual;

    @Column(name="trade_freeze")
    private String tradeFreeze;

    @Column(name="conv_approval")
    private Integer convApproval;

    @Column(name="trade_code")
    private Integer tradeCode;

    @Column(name="max_internal_marks")
    private Integer maxInternalMarks;

    @Column(name="type_admission")
    private String typeAdmission;

    @Column(name="dr_nondr")
    private String dr_Nondr;

    @Column(name="unit_strength")
    private Integer unitStrength;
    
    @Column(name="display_order")
    private Integer displayOrder;
}
