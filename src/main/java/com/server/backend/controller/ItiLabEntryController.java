package com.server.backend.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.server.backend.DTO.ItiLabsEntryDto;
import com.server.backend.service.ItiLabEntryService;
@Tag(name = "ItiLabEntryController", description = "Controller for handling ITI lab entries")
@RestController
@RequestMapping("/itilogin")
public class ItiLabEntryController {

    private final ItiLabEntryService itiLabEntryService;

    public ItiLabEntryController(ItiLabEntryService itiLabEntryService) {
        this.itiLabEntryService = itiLabEntryService;
    }

    @PostMapping("/lab-entry")
    public ResponseEntity<String> saveLabEntry(
            @RequestBody ItiLabsEntryDto dto) {

        itiLabEntryService.saveLabEntry(dto);

        return ResponseEntity.ok("Lab entry saved successfully");
    }
}