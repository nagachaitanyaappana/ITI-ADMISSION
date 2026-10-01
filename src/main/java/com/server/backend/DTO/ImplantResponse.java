package com.server.backend.DTO;
import java.time.LocalDate;
import java.time.LocalDateTime; 

public class ImplantResponse {

    private Long implantId;
    private String description;
    private Integer distcode;
    private LocalDateTime entryDate;
    private String facultyName;
    private LocalDate fromDate;
    private Long hrNo;
    private String industryAddress;
    private String itiCode;
    private String location;
    private Integer noOfDays;
    private Integer noOfStudents;
    private Long slno;
    private LocalDate toDate;
    private String tradeShort;
    private String entryBy;

    public Long getImplantId() {
        return implantId;
    }

    public void setImplantId(Long implantId) {
        this.implantId = implantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDistcode() {
        return distcode;
    }

    public void setDistcode(Integer distcode) {
        this.distcode = distcode;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Long getHrNo() {
        return hrNo;
    }

    public void setHrNo(Long hrNo) {
        this.hrNo = hrNo;
    }

    public String getIndustryAddress() {
        return industryAddress;
    }

    public void setIndustryAddress(String industryAddress) {
        this.industryAddress = industryAddress;
    }

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Integer getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(Integer noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public Long getSlno() {
        return slno;
    }

    public void setSlno(Long slno) {
        this.slno = slno;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getTradeShort() {
        return tradeShort;
    }

    public void setTradeShort(String tradeShort) {
        this.tradeShort = tradeShort;
    }

    public String getEntryBy() {
        return entryBy;
    }

    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }
}