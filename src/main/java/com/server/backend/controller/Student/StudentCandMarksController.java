package com.server.backend.controller.Student;

import com.server.backend.dto.StudentCandMarksDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.server.backend.service.StudentCandMarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student")
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