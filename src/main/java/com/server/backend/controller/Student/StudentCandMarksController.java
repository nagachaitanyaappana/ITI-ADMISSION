package com.server.backend.controller.Student;

import com.server.backend.DTO.StudentCandMarksDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.server.backend.service.Student.StudentCandMarksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student")
@RestController
@RequestMapping("/api/student/marks")
public class StudentCandMarksController {

    private final StudentCandMarksService service;

    public StudentCandMarksController(StudentCandMarksService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMarks(@RequestBody StudentCandMarksDto dto) {

        String response = service.saveMarks(dto);

        return ResponseEntity.ok(response);
    }
}