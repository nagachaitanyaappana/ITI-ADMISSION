package com.server.backend.entity.Placements;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.sql.Timestamp;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
@Entity
@Table(name="industry_partner_details" ,schema="implant")
@Data
public class IndustryPartnerDetails {
   @Id
@GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "industry_partner_details_seq")
@SequenceGenerator(
        name = "industry_partner_details_seq",
        sequenceName = "industry_partner_details_pid_seq",
        schema = "implant",
        allocationSize = 1)
@Column(name = "pid")
private Long pid;
    @Column(name="dist_code")
    private String distCode;

    @Column(name="entry_by")
    private String entryBy;

    @Column(name="entry_date")
    private Timestamp entryDate;

    @Column(name="iti_code")
    private String itiCode;

    @Column(name="proposed_new_trade")
    private String proposedNewTrade;

    @Column(name="revised_lead_industry_partner")
    private String revisedLeadIndustryPartner;

    @Column(name="revised_lead_sector")
    private String revisedLeadSector;
}
