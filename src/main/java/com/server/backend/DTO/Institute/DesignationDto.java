package com.server.backend.DTO.Institute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DesignationDto {
    @Size(max=4)
    private String desigCode;

    @NotBlank(message = "Designation is required")
    @Size(max = 50)
    private String designation;

    private Integer displayOrder;
}