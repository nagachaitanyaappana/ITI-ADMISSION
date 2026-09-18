package com.server.backend.DTO;
public record CurrentUser(
    String itiCode,
    String distCode,
    String insCode,
    String roleId
) {}
