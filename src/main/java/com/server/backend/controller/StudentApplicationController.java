package com.server.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.StudentRegistrationRequest;
import com.server.backend.entity.StudentApplication;
import com.server.backend.service.StudentApplicationService;

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

    @PutMapping("/update")
    public StudentApplication updateStudent(
            @RequestBody StudentRegistrationRequest request) {

        return service.updateStudent(request);
    }
}