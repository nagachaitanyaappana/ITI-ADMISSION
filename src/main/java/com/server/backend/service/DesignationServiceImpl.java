package com.server.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Institute.DesignationDto;
import com.server.backend.entity.Designation;
import com.server.backend.Repository.DesignationRepository;


@Service
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository repository;

    public DesignationServiceImpl(DesignationRepository repository) {
        this.repository = repository;
    }

    @Override
    public DesignationDto saveDesignation(DesignationDto dto) {

        Designation designation = new Designation();

        designation.setDesignation(dto.getDesignation());
        designation.setDisplayOrder(dto.getDisplayOrder());

        Designation saved = repository.save(designation);

        return mapToDto(saved);
    }

    @Override
    public List<DesignationDto> getAllDesignations() {

        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DesignationDto getDesignationById(Integer desigCode) {

        Designation designation = repository.findById(desigCode)
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        return mapToDto(designation);
    }

    @Override
    public DesignationDto updateDesignation(Integer desigCode, DesignationDto dto) {

        Designation designation = repository.findById(desigCode)
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        designation.setDesignation(dto.getDesignation());
        designation.setDisplayOrder(dto.getDisplayOrder());

        Designation updated = repository.save(designation);

        return mapToDto(updated);
    }

    @Override
    public void deleteDesignation(Integer desigCode) {

        if (!repository.existsById(desigCode)) {
            throw new RuntimeException("Designation not found");
        }

        repository.deleteById(desigCode);
    }

    private DesignationDto mapToDto(Designation designation) {

        DesignationDto dto = new DesignationDto();

        dto.setDesigCode(designation.getDesigCode());
        dto.setDesignation(designation.getDesignation());
        dto.setDisplayOrder(designation.getDisplayOrder());

        return dto;
    }
}