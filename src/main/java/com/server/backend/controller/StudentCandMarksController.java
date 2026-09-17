package com.server.backend.controller;

import com.server.backend.dto.StudentCandMarksDto;
import com.server.backend.service.StudentCandMarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/marks")
@RequiredArgsConstructor
public class StudentCandMarksController {

    private final StudentCandMarksService service;

    @PostMapping("/save")
    public ResponseEntity<String> saveMarks(@RequestBody StudentCandMarksDto dto) {

        String response = service.saveMarks(dto);

        return ResponseEntity.ok(response);
    }
}