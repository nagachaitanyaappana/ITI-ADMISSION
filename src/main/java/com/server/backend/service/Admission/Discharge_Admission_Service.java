package com.server.backend.service.Admission;


import com.server.backend.DTO.Discharge_Admission_DTO;

public interface Discharge_Admission_Service {

    Discharge_Admission_DTO getAdmission(
            String admissionNumber,
            String year);

}
