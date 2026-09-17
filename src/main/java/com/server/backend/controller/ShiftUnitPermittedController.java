package com.server.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.Institute.ShiftUnitPermittedRequestDto;
import com.server.backend.DTO.Institute.ShiftUnitPermittedResponseDto;
import com.server.backend.service.DGTPermittedShiftsUnits;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shift-unit-permitted")
@RequiredArgsConstructor
public class ShiftUnitPermittedController {

    private final DGTPermittedShiftsUnits service;

    // Save record
    @PutMapping
    public ResponseEntity<ShiftUnitPermittedResponseDto> save(
            @Valid @RequestBody ShiftUnitPermittedRequestDto dto) {

        return ResponseEntity.ok(service.save(dto));
    }

    // Get records by ITI and Trade
    @GetMapping
    public ResponseEntity<ShiftUnitPermittedResponseDto> getByItiAndTrade(
            @RequestParam String itiCode,
            @RequestParam String tradeCode) {

        return ResponseEntity.ok(
                service.getByItiAndTrade(itiCode, tradeCode));
    }

    // Delete a specific record using composite key
    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam String itiCode,
            @RequestParam String tradeCode,
            @RequestParam Integer shiftAllowed,
            @RequestParam Integer unitAllowed) {

        service.delete(
                itiCode,
                tradeCode,
                shiftAllowed,
                unitAllowed);

        return ResponseEntity.ok("Record deleted successfully");
    }
}