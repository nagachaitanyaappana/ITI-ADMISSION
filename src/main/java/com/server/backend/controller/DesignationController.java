package com.server.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Institute.DesignationDto;
import com.server.backend.service.DesignationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "designations", description = "Designation management operations")
@RestController
@RequestMapping("/api/designations")
public class DesignationController {

    private final DesignationService service;

    public DesignationController(DesignationService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new designation")
    @PostMapping
    public ResponseEntity<DesignationDto> createDesignation(
            @Valid @RequestBody DesignationDto dto) {

        return ResponseEntity.ok(service.saveDesignation(dto));
    }

    @Operation(summary = "Retrieve all designations")
    @GetMapping
    public ResponseEntity<List<DesignationDto>> getAllDesignations() {

        return ResponseEntity.ok(service.getAllDesignations());
    }

    @Operation(summary = "Get a designation by code")
    @GetMapping("/{desigCode}")
    public ResponseEntity<DesignationDto> getDesignationById(
            @PathVariable String desigCode) {

        return ResponseEntity.ok(service.getDesignationById(desigCode));
    }

    @Operation(summary = "Update an existing designation")
    @PutMapping("/{desigCode}")
    public ResponseEntity<DesignationDto> updateDesignation(
            @PathVariable String desigCode,
            @RequestBody DesignationDto dto) {

        return ResponseEntity.ok(service.updateDesignation(desigCode, dto));
    }

    @Operation(summary = "Delete a designation")
    @DeleteMapping("/{desigCode}")
    public ResponseEntity<String> deleteDesignation(
            @PathVariable String desigCode) {

        service.deleteDesignation(desigCode);

        return ResponseEntity.ok("Designation deleted successfully");
    }
}