package com.server.backend.DTO;

public class Admission_Image_Upload_DTO {

    private String admissionNumber;
    private String sscHallticketNumber;
    private String registrationId;
    private String name;

    public Admission_Image_Upload_DTO() {
    }

    public Admission_Image_Upload_DTO(
            String admissionNumber,
            String sscHallticketNumber,
            String registrationId,
            String name) {

        this.admissionNumber = admissionNumber;
        this.sscHallticketNumber = sscHallticketNumber;
        this.registrationId = registrationId;
        this.name = name;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getSscHallticketNumber() {
        return sscHallticketNumber;
    }

    public void setSscHallticketNumber(String sscHallticketNumber) {
        this.sscHallticketNumber = sscHallticketNumber;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
