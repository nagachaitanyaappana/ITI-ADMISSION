package com.server.backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.Institute.DesignationDto;
import com.server.backend.service.DesignationService;

@RestController
@RequestMapping("/api/designations")
public class DesignationController {

    private final DesignationService service;

    public DesignationController(DesignationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DesignationDto> createDesignation(
            @Valid @RequestBody DesignationDto dto) {

        return ResponseEntity.ok(service.saveDesignation(dto));
    }

    @GetMapping
    public ResponseEntity<List<DesignationDto>> getAllDesignations() {

        return ResponseEntity.ok(service.getAllDesignations());
    }

    @GetMapping("/{desigCode}")
    public ResponseEntity<DesignationDto> getDesignationById(
            @PathVariable String desigCode) {

        return ResponseEntity.ok(service.getDesignationById(desigCode));
    }

    @PutMapping("/{desigCode}")
    public ResponseEntity<DesignationDto> updateDesignation(
            @PathVariable String desigCode,
            @RequestBody DesignationDto dto) {

        return ResponseEntity.ok(service.updateDesignation(desigCode, dto));
    }

    @DeleteMapping("/{desigCode}")
    public ResponseEntity<String> deleteDesignation(
            @PathVariable String desigCode) {

        service.deleteDesignation(desigCode);

        return ResponseEntity.ok("Designation deleted successfully");
    }
}