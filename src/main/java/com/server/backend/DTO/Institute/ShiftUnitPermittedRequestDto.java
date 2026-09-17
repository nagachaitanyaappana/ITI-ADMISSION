package com.server.backend.DTO.Institute;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ShiftUnitPermittedRequestDto {

    @NotBlank(message = "ITI code is required")
    private String itiCode;

    @NotBlank(message = "Trade code is required")
    private String tradeCode;

    private Boolean availableYear;

    @Valid
    @NotEmpty(message = "At least one entry is required")
    private List<ShiftUnitEntryDto> entries;
}