package com.server.backend.service.ITI;

import java.util.List;

import com.server.backend.DTO.DesignationDto;

public interface DesignationService {

    DesignationDto saveDesignation(DesignationDto dto);

    List<DesignationDto> getAllDesignations();

    DesignationDto getDesignationById(String desigCode);

    DesignationDto updateDesignation(String desigCode, DesignationDto dto);

    void deleteDesignation(String desigCode);
}