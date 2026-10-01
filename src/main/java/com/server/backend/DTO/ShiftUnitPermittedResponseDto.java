package com.server.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ShiftUnitPermittedResponseDto {

    private String itiCode;
    private String tradeCode;
    private Boolean availableYear;
    private List<ShiftUnitEntryDto> entries;
}