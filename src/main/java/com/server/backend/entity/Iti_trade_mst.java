package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Legacy stores subject code -> max internal marks as a PostgreSQL hstore
     * (e.g. "B"=>"150"). Mapping it as Integer made EVERY read of this table
     * fail with DataIntegrityViolation ("Bad value for type int : ...").
     * Map<String,String> + columnDefinition="hstore" is how the sibling
     * Ramya project maps the same column.
     */
    @Column(name="max_internal_marks", columnDefinition = "hstore")
    private Map<String, String> maxInternalMarks = new HashMap<>();

    @Column(name="type_admission")
    private String typeAdmission;

    @Column(name="dr_nondr")
    private String dr_Nondr;

    @Column(name="unit_strength")
    private Integer unitStrength;
    
    @Column(name="display_order")
    private Integer displayOrder;
}
