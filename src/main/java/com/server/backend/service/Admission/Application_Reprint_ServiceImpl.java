package com.server.backend.service.Admission;


import org.springframework.stereotype.Service;

import com.server.backend.DTO.Application_Reprint_DTO;
import com.server.backend.Repository.Admission.Application_Reprint_Repository;
import com.server.backend.entity.StudentApplication;

@Service
public class Application_Reprint_ServiceImpl
        implements Application_Reprint_Service {

    private final Application_Reprint_Repository repository;

    public Application_Reprint_ServiceImpl(
            Application_Reprint_Repository repository) {

        this.repository = repository;
    }

    @Override
    public Application_Reprint_DTO getApplication(
            Long registrationId) {

        StudentApplication application = repository
                .findByRegid(registrationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Application not found for the given Registration Id"));

        return new Application_Reprint_DTO(
                String.valueOf(application.getRegid()),
                application.getName(),
                application.getFname(),
                application.getMname(),
                application.getGender(),
                application.getDob(),
                application.getAdarno(),
                application.getPhno(),
                application.getEmail(),
                application.getAddr(),
                application.getPincode(),
                application.getCaste(),
                application.getSubCaste(),
                application.getLocal(),
                application.getEconomicWeakerSection(),
                application.getPhc(),
                application.getPwdCategory(),
                application.getExservice(),
                application.getYear(),
                application.getTrno(),
                application.getPhase(),
                application.getAppStatus()
        );
    }
}