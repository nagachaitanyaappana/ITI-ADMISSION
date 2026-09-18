package com.server.backend.DTO.ITI;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShiftUnitEntryDto {

    @NotNull(message = "Strength is required")
    private Integer strength;

    @NotNull(message = "Shift allowed is required")
    @Min(value = 1, message = "Shift must be at least 1")
    @Max(value = 3, message = "Shift must be at most 3")
    private Integer shiftAllowed;

    @NotNull(message = "Unit allowed is required")
    @Min(value = 1, message = "Unit must be at least 1")
    @Max(value = 12, message = "Unit must be at most 12")
    private Integer unitAllowed;

    private Integer strengthVacant;
}