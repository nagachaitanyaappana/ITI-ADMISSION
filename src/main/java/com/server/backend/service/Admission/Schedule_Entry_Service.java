package com.server.backend.service.Admission;

import java.util.List;

import com.server.backend.DTO.Schedule_Entry_DTO;

public interface Schedule_Entry_Service {

    List<Schedule_Entry_DTO> getScheduleEntries(
            String qualification,
            String caste,
            String phase,
            String year);
}