package com.server.backend.service.Admission;



import com.server.backend.DTO.Admission_Update_DTO;

public interface Admission_Update_Service {

    Admission_Update_DTO getAdmission(String admissionNumber);

}
