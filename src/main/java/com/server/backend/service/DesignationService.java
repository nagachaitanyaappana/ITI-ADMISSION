package com.server.backend.service;

import java.util.List;

import com.server.backend.DTO.Institute.DesignationDto;

public interface DesignationService {

    DesignationDto saveDesignation(DesignationDto dto);

    List<DesignationDto> getAllDesignations();

    DesignationDto getDesignationById(Integer desigCode);

    DesignationDto updateDesignation(Integer desigCode, DesignationDto dto);

    void deleteDesignation(Integer desigCode);
}