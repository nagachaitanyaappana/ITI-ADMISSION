package com.server.backend.entity.labs;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "labs",schema="labs")
public class Labs {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "lab_id")
private Long labId;

    @Column(name = "entry_by")
    private String entryBy;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "industry_name")
    private String industryName;

    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "trade_short")
    private String tradeShort;

    @Column(name = "description")
    private String description;

    public Labs() {
    }

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }

    public String getEntryBy() {
        return entryBy;
    }

    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getTradeShort() {
        return tradeShort;
    }

    public void setTradeShort(String tradeShort) {
        this.tradeShort = tradeShort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
