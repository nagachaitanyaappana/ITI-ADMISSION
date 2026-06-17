package com.server.backend.service;

import java.util.List;

import com.server.backend.DTO.Institute.DesignationDto;

public interface DesignationService {

    DesignationDto saveDesignation(DesignationDto dto);

    List<DesignationDto> getAllDesignations();

    DesignationDto getDesignationById(String desigCode);

    DesignationDto updateDesignation(String desigCode, DesignationDto dto);

    void deleteDesignation(String desigCode);
}