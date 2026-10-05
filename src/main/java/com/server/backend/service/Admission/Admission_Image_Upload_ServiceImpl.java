package com.server.backend.service.Admission;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Admission_Image_Upload_DTO;
import com.server.backend.Repository.Admission.Admission_Image_Upload_Repository;
import com.server.backend.entity.iti_admissions;

@Service
public class Admission_Image_Upload_ServiceImpl
        implements Admission_Image_Upload_Service {

    private final Admission_Image_Upload_Repository repository;

    public Admission_Image_Upload_ServiceImpl(
            Admission_Image_Upload_Repository repository) {

        this.repository = repository;
    }

    @Override
    public Admission_Image_Upload_DTO verifyAdmission(
            String admissionNumber,
            String sscHallticketNumber) {

        iti_admissions admission = repository
                .findAdmissionForImageUpload(
                        admissionNumber,
                        sscHallticketNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Admission Number and SSC Hallticket Number do not match"));

        return new Admission_Image_Upload_DTO(
                admission.getAdm_num(),
                admission.getSsc_regno(),
                admission.getRegid(),
                admission.getName()
        );
    }

}
