package com.server.backend.DTO.Reports;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public class ItiDto {

    @NotBlank(message = "ITI Code is required")
    private String itiCode;

    @NotBlank(message = "ITI Name is required")
    @Size(max = 100)
    private String itiName;

    @NotNull(message = "District Code is required")
    private Character distCode;

    private Character govt;

    @Min(value = 0)
    private Integer capacity;

    private Integer allocated;

    private Integer remainingCapacity;

    private Integer totStrength;

    @Size(max = 250)
    private String address;

    @Size(max = 100)
    private String cityTown;

    @Email(message = "Invalid email")
    private String email;

    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Mobile must be 10 digits"
    )
    private String mobile;

    private String principalName;

    // Getters and Setters

    public String getItiCode() {
        return itiCode;
    }

    public void setItiCode(String itiCode) {
        this.itiCode = itiCode;
    }

    public String getItiName() {
        return itiName;
    }

    public void setItiName(String itiName) {
        this.itiName = itiName;
    }

    public Character getDistCode() {
        return distCode;
    }

    public void setDistCode(Character distCode) {
        this.distCode = distCode;
    }

    public Character getGovt() {
        return govt;
    }

    public void setGovt(Character govt) {
        this.govt = govt;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAllocated() {
        return allocated;
    }

    public void setAllocated(Integer allocated) {
        this.allocated = allocated;
    }

    public Integer getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(Integer remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public Integer getTotStrength() {
        return totStrength;
    }

    public void setTotStrength(Integer totStrength) {
        this.totStrength = totStrength;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityTown() {
        return cityTown;
    }

    public void setCityTown(String cityTown) {
        this.cityTown = cityTown;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }
}