package com.server.backend.service.Admission;



import com.server.backend.DTO.Admission_Image_Upload_DTO;

public interface Admission_Image_Upload_Service {

    Admission_Image_Upload_DTO verifyAdmission(
            String admissionNumber,
            String sscHallticketNumber);

}
