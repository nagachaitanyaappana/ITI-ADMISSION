package com.server.backend.DTO.AdmissionTimingsDTOs;
public record CurrentUser(
    String itiCode,
    String distCode,
    String insCode,
    String roleId
) {}
