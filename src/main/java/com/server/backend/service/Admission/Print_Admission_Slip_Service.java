package com.server.backend.service.Admission;

import com.server.backend.DTO.Print_Admission_slip_DTO;

public interface Print_Admission_Slip_Service {

    Print_Admission_slip_DTO getAdmissionSlip(String admissionNumber);
}