package com.server.backend.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FreezeUnfreezeRequest(
        @NotBlank String recordId,
        @NotNull Boolean freeze
) {

}
