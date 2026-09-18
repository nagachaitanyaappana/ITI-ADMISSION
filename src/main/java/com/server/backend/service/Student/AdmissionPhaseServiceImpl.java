package com.server.backend.service.Student;

import com.server.backend.DTO.AdmissionPhaseDto;
import com.server.backend.entity.AdmissionPhase;
import com.server.backend.entity.AdmissionPhaseId;
import com.server.backend.Repository.Student.AdmissionPhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmissionPhaseServiceImpl implements AdmissionPhaseService {

    @Autowired
    private AdmissionPhaseRepository repository;

    @Override
    public AdmissionPhaseDto saveAdmissionPhase(AdmissionPhaseDto dto) {

        AdmissionPhase entity = new AdmissionPhase();

        entity.setYear(dto.getYear());
        entity.setPhase(dto.getPhase());
        entity.setItiType(dto.getItiType());

        entity.setFromDate(dto.getFromDate());
        entity.setToDate(dto.getToDate());

        entity.setSeatmatrixPhase(dto.getSeatmatrixPhase());
        entity.setAdmissionRole(dto.getAdmissionRole());
        entity.setLid(dto.getLid());
        entity.setCurrent(dto.getCurrent());
        entity.setTrno(dto.getTrno());

        entity.setApplicationFromDate(dto.getApplicationFromDate());
        entity.setApplicationToDate(dto.getApplicationToDate());

        entity.setVerificationStartDate(dto.getVerificationStartDate());
        entity.setVerificationEndDate(dto.getVerificationEndDate());

        entity.setMeritListStartDate(dto.getMeritListStartDate());
        entity.setMeritListToDate(dto.getMeritListToDate());

        entity.setAdmissionsStartDate(dto.getAdmissionsStartDate());
        entity.setAdmissionsEndDate(dto.getAdmissionsEndDate());

        entity.setReverificationStartDate(dto.getReverificationStartDate());
        entity.setReverificationEndDate(dto.getReverificationEndDate());

        repository.save(entity);

        return dto;
    }

    @Override
    public List<AdmissionPhase> getAllAdmissionPhases() {
        return repository.findAll();
    }
    @Override
public AdmissionPhase getAdmissionPhase(String year, Integer phase, String itiType) {

    AdmissionPhaseId id = new AdmissionPhaseId(year, phase, itiType);

    return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Admission Phase Not Found"));
}
@Override
public AdmissionPhaseDto updateAdmissionPhase(String year,
                                              Integer phase,
                                              String itiType,
                                              AdmissionPhaseDto dto) {

    AdmissionPhaseId id = new AdmissionPhaseId(year, phase, itiType);

    AdmissionPhase entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Admission Phase Not Found"));

    entity.setFromDate(dto.getFromDate());
    entity.setToDate(dto.getToDate());

    entity.setSeatmatrixPhase(dto.getSeatmatrixPhase());
    entity.setAdmissionRole(dto.getAdmissionRole());
    entity.setLid(dto.getLid());
    entity.setCurrent(dto.getCurrent());
    entity.setTrno(dto.getTrno());

    entity.setApplicationFromDate(dto.getApplicationFromDate());
    entity.setApplicationToDate(dto.getApplicationToDate());

    entity.setVerificationStartDate(dto.getVerificationStartDate());
    entity.setVerificationEndDate(dto.getVerificationEndDate());

    entity.setMeritListStartDate(dto.getMeritListStartDate());
    entity.setMeritListToDate(dto.getMeritListToDate());

    entity.setAdmissionsStartDate(dto.getAdmissionsStartDate());
    entity.setAdmissionsEndDate(dto.getAdmissionsEndDate());

    entity.setReverificationStartDate(dto.getReverificationStartDate());
    entity.setReverificationEndDate(dto.getReverificationEndDate());

    repository.save(entity);

    return dto;
}
@Override
public String deleteAdmissionPhase(String year, Integer phase, String itiType) {

    AdmissionPhaseId id = new AdmissionPhaseId(year, phase, itiType);

    AdmissionPhase entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Admission Phase Not Found"));

    repository.delete(entity);

    return "Admission Phase Deleted Successfully";
}
}