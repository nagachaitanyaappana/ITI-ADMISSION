package com.server.backend.entity;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import jakarta.persistence.Column;
import java.sql.Timestamp;

@Entity
@Table(name="industry_master" ,schema="implant")
@Data
public class IndustryMaster {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "industry_master_seq"
    )
    @SequenceGenerator(
        name = "industry_master_seq",
        sequenceName = "implant.industry_master_id_seq",
        allocationSize = 1)
    @Column(name="industry_id")    
    private Long industryId;
    @Column(name="industry_address")
    private String industryAddress;

    @Column(name="industry_name")
    private String industryName;

    @Column(name="industry_type")
    private String industryType;

    @Column(name="entry_by")
    private String entryBy;

    @Column(name="entry_time")
    private Timestamp entryTime;

    @Column(name="edit_by")
    private String editBy;
    
    @Column(name="edit_time")
    private Timestamp editTime;
}
