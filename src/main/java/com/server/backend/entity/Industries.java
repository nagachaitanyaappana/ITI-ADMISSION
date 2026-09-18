package com.server.backend.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Column;
import java.sql.Timestamp;
@Entity
@Table(name="industries" ,schema="implant")
@Data
public class Industries {
  @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "industries_seq")
@SequenceGenerator(
    name = "industries_seq",
    sequenceName = "industries_id_seq",
    schema = "implant",
    allocationSize = 1
)
@Column(name = "slno")
private Long slno;
     @Column(name="dist_code")
     private Integer distCode;
     
     @Column(name="dist_name")
     private String distName;

     @Column(name="industry_id")
     private Long industryId;

     @Column(name="industry_name")
     private String industryName;

     @Column(name="industry_type")
     private String industryType;

     @Column(name="iti_code")
     private Integer itiCode;

     @Column(name="iti_name")
     private String itiName;

     @Column(name="ncvt_mis_code")
     private String ncvtMisCode;

     @Column(name="no_of_trades")
     private String noOfTrades;

     @Column(name="no_of_units")
     private String noOfUnits;

     @Column(name="trade_code")
     private Integer tradeCode;

     @Column(name="trade_name")
     private String tradeName;

     @Column(name="entry_by")
     private String entryBy;

     @Column(name="entry_time")
     private Timestamp entryTime;

     @Column(name="trade_short")
    private String tradeShort;
}
