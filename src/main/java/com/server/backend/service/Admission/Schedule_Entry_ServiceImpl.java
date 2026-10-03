package com.server.backend.service.Admission;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Schedule_Entry_DTO;
import com.server.backend.Repository.Admission.Admission_Timing_Repository;
import com.server.backend.entity.AdmissionTiming;

@Service
public class Schedule_Entry_ServiceImpl implements Schedule_Entry_Service {

    private final Admission_Timing_Repository repository;

    public Schedule_Entry_ServiceImpl(
            Admission_Timing_Repository repository) {

        this.repository = repository;
    }

    @Override
    public List<Schedule_Entry_DTO> getScheduleEntries(
            String qualification,
            String caste,
            String phase,
            String year) {

        List<AdmissionTiming> timings =
                repository.findByMinqulAndCasteAndPhaseAndYear(
                        qualification,
                        caste,
                        phase,
                        year);

        return timings.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private Schedule_Entry_DTO convertToDTO(
            AdmissionTiming timing) {

        return new Schedule_Entry_DTO(
                timing.getItiCode(),
                timing.getMinqul(),
                timing.getMeritFrom(),
                timing.getMeritTo(),
                timing.getCalDate(),
                timing.getCalTime(),
                timing.getDistCode(),
                timing.getCaste(),
                timing.getTrno(),
                timing.getTempPk(),
                timing.getPhase(),
                timing.getYear()
        );
    }
}