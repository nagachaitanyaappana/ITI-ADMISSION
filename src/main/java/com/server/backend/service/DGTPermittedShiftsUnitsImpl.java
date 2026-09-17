package com.server.backend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.backend.DTO.Institute.ShiftUnitEntryDto;
import com.server.backend.DTO.Institute.ShiftUnitPermittedRequestDto;
import com.server.backend.DTO.Institute.ShiftUnitPermittedResponseDto;
import com.server.backend.entity.ShiftUnitPermitted;
import com.server.backend.entity.ShiftUnitPermittedId;
import com.server.backend.Repository.ShiftUnitPermittedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DGTPermittedShiftsUnitsImpl implements DGTPermittedShiftsUnits {

    private final ShiftUnitPermittedRepository repository;

    @Override
    @Transactional
    public ShiftUnitPermittedResponseDto save(ShiftUnitPermittedRequestDto dto) {

        // 1. Validate all entries together (including duplicate check)
        validateEntries(dto.getEntries());

        // 2. Delete all existing records for this ITI + Trade
        repository.deleteByItiCodeAndTradeCode(dto.getItiCode(), dto.getTradeCode());

        // 3. Save each entry
        List<ShiftUnitEntryDto> savedEntries = new ArrayList<>();

        for (ShiftUnitEntryDto entryDto : dto.getEntries()) {
            ShiftUnitPermitted entity = mapToEntity(dto, entryDto);
            ShiftUnitPermitted savedEntity = repository.save(entity);
            savedEntries.add(mapToEntryDto(savedEntity));
        }

        // 4. Build and return response
        return buildResponse(dto, savedEntries);
    }

    @Override
    @Transactional(readOnly = true)
    public ShiftUnitPermittedResponseDto getByItiAndTrade(
            String itiCode,
            String tradeCode) {

        List<ShiftUnitPermitted> entities = repository.findByItiCodeAndTradeCode(
                itiCode, tradeCode);

        if (entities.isEmpty()) {
            return new ShiftUnitPermittedResponseDto();
        }

        List<ShiftUnitEntryDto> entries = entities.stream()
                .map(this::mapToEntryDto)
                .collect(Collectors.toList());

        ShiftUnitPermittedResponseDto response = new ShiftUnitPermittedResponseDto();
        response.setItiCode(itiCode);
        response.setTradeCode(tradeCode);
        response.setAvailableYear(entities.get(0).getAvailableYear());
        response.setEntries(entries);

        return response;
    }

    @Override
    @Transactional
    public void delete(String itiCode,
                       String tradeCode,
                       Integer shiftAllowed,
                       Integer unitAllowed) {

        ShiftUnitPermittedId id = new ShiftUnitPermittedId(
                itiCode,
                tradeCode,
                String.valueOf(shiftAllowed),
                String.valueOf(unitAllowed));

        repository.deleteById(id);
    }

    // ---------------- Batch Validation ----------------

    private void validateEntries(List<ShiftUnitEntryDto> entries) {

        if (entries == null || entries.isEmpty()) {
            throw new IllegalArgumentException("At least one entry is required");
        }

        Set<String> seenCombinations = new HashSet<>();

        for (ShiftUnitEntryDto entry : entries) {
            validateEntry(entry);

            String combination = entry.getShiftAllowed() + "-" + entry.getUnitAllowed();

            if (seenCombinations.contains(combination)) {
                throw new IllegalArgumentException(
                        "Duplicate shift and unit combination found: shift " +
                                entry.getShiftAllowed() + ", unit " +
                                entry.getUnitAllowed());
            }

            seenCombinations.add(combination);
        }
    }

    // ---------------- Single Entry Validation ----------------

    private void validateEntry(ShiftUnitEntryDto dto) {

        // Strength: only 20 or 24
        if (dto.getStrength() == null ||
                (dto.getStrength() != 20 && dto.getStrength() != 24)) {

            throw new IllegalArgumentException(
                    "Strength must be either 20 or 24");
        }

        // Shift: only 1 to 3
        if (dto.getShiftAllowed() == null ||
                dto.getShiftAllowed() < 1 || dto.getShiftAllowed() > 3) {

            throw new IllegalArgumentException(
                    "Shift allowed must be between 1 and 3");
        }

        // Unit: only 1 to 12
        if (dto.getUnitAllowed() == null ||
                dto.getUnitAllowed() < 1 || dto.getUnitAllowed() > 12) {

            throw new IllegalArgumentException(
                    "Unit allowed must be between 1 and 12");
        }

        // Strength vacant: cannot be negative
        if (dto.getStrengthVacant() != null &&
                dto.getStrengthVacant() < 0) {

            throw new IllegalArgumentException(
                    "Strength vacant cannot be negative");
        }
    }

    // ---------------- Mapping ----------------

    private ShiftUnitPermitted mapToEntity(
            ShiftUnitPermittedRequestDto requestDto,
            ShiftUnitEntryDto entryDto) {

        ShiftUnitPermitted entity = new ShiftUnitPermitted();

        entity.setItiCode(requestDto.getItiCode());
        entity.setTradeCode(requestDto.getTradeCode());
        entity.setAvailableYear(requestDto.getAvailableYear());

        entity.setShiftAllowed(String.valueOf(entryDto.getShiftAllowed()));
        entity.setUnitAllowed(String.valueOf(entryDto.getUnitAllowed()));
        entity.setStrength(entryDto.getStrength());
        entity.setStrengthVacant(entryDto.getStrengthVacant());

        return entity;
    }

    private ShiftUnitEntryDto mapToEntryDto(ShiftUnitPermitted entity) {

        ShiftUnitEntryDto dto = new ShiftUnitEntryDto();

        dto.setStrength(entity.getStrength());
        dto.setShiftAllowed(Integer.valueOf(entity.getShiftAllowed()));
        dto.setUnitAllowed(Integer.valueOf(entity.getUnitAllowed()));
        dto.setStrengthVacant(entity.getStrengthVacant());

        return dto;
    }

    private ShiftUnitPermittedResponseDto buildResponse(
            ShiftUnitPermittedRequestDto requestDto,
            List<ShiftUnitEntryDto> entries) {

        ShiftUnitPermittedResponseDto response = new ShiftUnitPermittedResponseDto();

        response.setItiCode(requestDto.getItiCode());
        response.setTradeCode(requestDto.getTradeCode());
        response.setAvailableYear(requestDto.getAvailableYear());
        response.setEntries(entries);

        return response;
    }
}