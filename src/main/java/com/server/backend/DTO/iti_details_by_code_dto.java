package com.server.backend.DTO;


public class iti_details_by_code_dto {

    private String itiCode;
    private String itiName;
    private String govt;
    private String distCode;
    private Integer capacity;
    private Integer allocated;
    private Integer remainingCapacity;
    private String address;

    public iti_details_by_code_dto() {
    }

    public iti_details_by_code_dto(String itiCode, String itiName, String govt,
                                   String distCode, Integer capacity,
                                   Integer allocated, Integer remainingCapacity,
                                   String address) {
        this.itiCode = itiCode;
        this.itiName = itiName;
        this.govt = govt;
        this.distCode = distCode;
        this.capacity = capacity;
        this.allocated = allocated;
        this.remainingCapacity = remainingCapacity;
        this.address = address;
    }

    public String getItiCode() { return itiCode; }
    public void setItiCode(String itiCode) { this.itiCode = itiCode; }

    public String getItiName() { return itiName; }
    public void setItiName(String itiName) { this.itiName = itiName; }

    public String getGovt() { return govt; }
    public void setGovt(String govt) { this.govt = govt; }

    public String getDistCode() { return distCode; }
    public void setDistCode(String distCode) { this.distCode = distCode; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getAllocated() { return allocated; }
    public void setAllocated(Integer allocated) { this.allocated = allocated; }

    public Integer getRemainingCapacity() { return remainingCapacity; }
    public void setRemainingCapacity(Integer remainingCapacity) { this.remainingCapacity = remainingCapacity; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}