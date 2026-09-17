package com.server.backend.DTO.AdmissionTimingsDTOs;
import java.time.LocalTime;
public record ScheduleViewResponse(
    String date,
    String meritRange,
    String caste,
    String qualification,
    LocalTime calTime
) {}
    

