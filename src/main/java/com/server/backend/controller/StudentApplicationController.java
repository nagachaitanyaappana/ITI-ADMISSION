package com.server.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.StudentRegistrationRequest;
import com.server.backend.entity.StudentApplication;
import com.server.backend.service.StudentApplicationService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "student-applications", description = "Student application management operations")
@RestController
@RequestMapping("/api/student")
public class StudentApplicationController {

    private final StudentApplicationService service;

    public StudentApplicationController(StudentApplicationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public StudentApplication register(
            @RequestBody StudentRegistrationRequest request) {

        return service.saveStudent(request);
    }
}