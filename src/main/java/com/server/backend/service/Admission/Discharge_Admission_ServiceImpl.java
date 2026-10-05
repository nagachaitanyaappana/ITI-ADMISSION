package com.server.backend.service.Admission;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Discharge_Admission_DTO;
import com.server.backend.Repository.Admission.Discharge_Admission_Repository;
import com.server.backend.entity.iti_admissions;

@Service
public class Discharge_Admission_ServiceImpl
        implements Discharge_Admission_Service {

    private final Discharge_Admission_Repository repository;

    public Discharge_Admission_ServiceImpl(
            Discharge_Admission_Repository repository) {
        this.repository = repository;
    }

    @Override
    public Discharge_Admission_DTO getAdmission(
            String admissionNumber,
            String year) {

        String adm = admissionNumber == null ? "" : admissionNumber.trim();
        String yr = year == null ? "" : year.trim();

        iti_admissions admission = repository
                .findByAdmissionNumberAndYear(adm, yr)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Admission number not found for the given year"));

        return new Discharge_Admission_DTO(
                admission.getAdm_num(),
                admission.getYear_of_admission()
        );
    }

}
