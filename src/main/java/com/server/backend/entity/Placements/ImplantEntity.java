package com.server.backend.entity.Placements;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="implant", schema="implant")
public class ImplantEntity{
    
    
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "implant_id_seq")
@SequenceGenerator(
    name = "implant_id_seq",
    sequenceName = "public2.implant_seq",
    allocationSize = 1
)
@Column(name = "implant_id", nullable = false)
private Long implantId;

@Column(name = "faculty_name")
private String facultyName;

@Column(name = "industry_address")
private String industryAddress;

@Column(name = "location")
private String location;

@Column(name = "hr_no")
private Long hrNo;

@Column(name = "from_date")
private LocalDate fromDate;

@Column(name = "to_date")
private LocalDate toDate;

@Column(name = "no_of_students")
private Integer noOfStudents;

@Column(name = "distcode")
private Integer distCode;

@Column(name = "description")
private String description;

@Column(name = "iti_code")
private String itiCode;

@Column(name = "entry_by")
private String entryBy;

@Column(name = "entry_date")
private LocalDateTime entryDate;

@Column(name = "no_of_days")
private Integer noOfDays;

@Column(name = "trade_short")
private String tradeShort;

@Column(name = "slno")
private Long slno;
    public Long getImplantId() {
        return implantId;
    }

    public void setImplantId(Long implantId) {
        this.implantId = implantId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getIndustryAddress() {
        return industryAddress;
    }

    public void setIndustryAddress(String industryAddress) {
        this.industryAddress = industryAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getHrNo() {
        return hrNo;
    }

    public void setHrNo(Long hrNo) {
        this.hrNo = hrNo;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(Integer noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public Integer getDistCode() {
        return distCode;
    }

    public void setDistCode(Integer distCode) {
        this.distCode = distCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
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

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getTradeShort() {
        return tradeShort;
    }

    public void setTradeShort(String tradeShort) {
        this.tradeShort = tradeShort;
    }

    public Long getSlno() {
        return slno;
    }

    public void setSlno(Long slno) {
        this.slno = slno;
    }
}
