package com.server.backend.service.Admission;


import org.springframework.stereotype.Service;

import com.server.backend.DTO.Print_Admission_slip_DTO;
import com.server.backend.entity.iti_admissions;
import com.server.backend.Repository.Admission.Print_Admission_Slip_Repository;

@Service
public class Print_Admission_Slip_ServiceImpl
        implements Print_Admission_Slip_Service {

    private final Print_Admission_Slip_Repository repository;

    public Print_Admission_Slip_ServiceImpl(
            Print_Admission_Slip_Repository repository) {
        this.repository = repository;
    }

    @Override
    public Print_Admission_slip_DTO getAdmissionSlip(
            String admissionNumber) {

        iti_admissions admission = repository
                .findByAdm_num(admissionNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Admission number not found"));

        return new Print_Admission_slip_DTO(
                admission.getName(),
                admission.getFname(),
                admission.getDob(),
                admission.getRegid(),
                admission.getCaste(),
                admission.getRes_category(),
                admission.getDate_of_admission(),
                admission.getIti_code(),
                admission.getTrade_code() == null
                        ? null
                        : admission.getTrade_code().toString(),
                admission.getAdm_num()
        );
    }
}