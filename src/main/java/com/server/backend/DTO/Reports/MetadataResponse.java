package com.server.backend.DTO.Reports;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetadataResponse {
    private List<String> years;
    private List<String> phases;
    private List<String> castes;
    private List<String> levels;
}