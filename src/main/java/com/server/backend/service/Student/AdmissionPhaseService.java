package com.server.backend.service.Student;

import com.server.backend.DTO.AdmissionPhaseDto;
import com.server.backend.entity.AdmissionPhase;
import java.util.List;
public interface AdmissionPhaseService {

    AdmissionPhaseDto saveAdmissionPhase(AdmissionPhaseDto dto);
List<AdmissionPhase> getAllAdmissionPhases();
AdmissionPhase getAdmissionPhase(String year, Integer phase, String itiType);
String deleteAdmissionPhase(String year, Integer phase, String itiType);
AdmissionPhaseDto updateAdmissionPhase(
        String year,
        Integer phase,
        String itiType,
        AdmissionPhaseDto dto);
}