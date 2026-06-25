package com.server.backend.service;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.StudentRegistrationRequest;
import com.server.backend.Repository.StudentApplicationRepository;
import com.server.backend.entity.StudentApplication;

@Service
public class StudentApplicationService {

    private final StudentApplicationRepository repository;

    public StudentApplicationService(StudentApplicationRepository repository) {
        this.repository = repository;
    }

    public StudentApplication saveStudent(StudentRegistrationRequest request) {

        StudentApplication student = new StudentApplication();

        student.setSscBoard(request.getSscBoard());
        student.setSscRegno(request.getHallTicketNumber());
        student.setSscYear(request.getSscYear());
        student.setSscMonth(request.getSscMonth());
        student.setSscType(request.getSscType());

        return repository.save(student);
    }

    public StudentApplication updateStudent(StudentRegistrationRequest request) {

        StudentApplication student =
                repository.findById(request.getHallTicketNumber())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(request.getName());
        student.setFname(request.getFname());
        student.setMname(request.getMname());
        student.setPhno(request.getPhno());
        student.setAdarno(request.getAdarno());
        student.setGender(request.getGender());
        student.setCaste(request.getCaste());
        student.setLocal(request.getLocal());
        student.setPincode(request.getPincode());
        student.setEmail(request.getEmail());
        student.setAddr(request.getAddr());

        return repository.save(student);
    }
}