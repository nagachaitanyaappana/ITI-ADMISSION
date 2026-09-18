package com.server.backend.DTO;
import java.time.LocalTime;
public record ScheduleViewResponse(
    String date,
    String meritRange,
    String caste,
    String qualification,
    LocalTime calTime
) {}
    

