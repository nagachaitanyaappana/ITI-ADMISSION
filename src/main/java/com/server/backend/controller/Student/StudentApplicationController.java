package com.server.backend.controller.Student;

import com.server.backend.dto.StudentApplicationDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.server.backend.service.StudentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.server.backend.entity.CasteMaster;
import com.server.backend.entity.SubCasteMaster;
@Tag(name = "Student")
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentApplicationController {

    private final StudentApplicationService service;

    // Save Student
    @PostMapping("/save")
    public ResponseEntity<StudentApplicationDto> saveStudent(
            @RequestBody StudentApplicationDto dto) {

        return new ResponseEntity<>(service.saveStudent(dto), HttpStatus.CREATED);
    }

    // Get Student By RegId
    @GetMapping("/{regid}")
    public ResponseEntity<StudentApplicationDto> getStudentById(
            @PathVariable Integer regid) {

        return ResponseEntity.ok(service.getStudentById(regid));
    }

    // Get Student By Hall Ticket
    @GetMapping("/hallticket/{sscRegNo}")
    public ResponseEntity<StudentApplicationDto> getStudentByHallTicket(
            @PathVariable String sscRegNo) {

        return ResponseEntity.ok(service.getStudentByHallTicket(sscRegNo));
    }

    // Get All Students
    @GetMapping("/all")
    public ResponseEntity<List<StudentApplicationDto>> getAllStudents() {

        return ResponseEntity.ok(service.getAllStudents());
    }

    // Update Student
    @PutMapping("/update/{regid}")
    public ResponseEntity<StudentApplicationDto> updateStudent(
            @PathVariable Integer regid,
            @RequestBody StudentApplicationDto dto) {

        return ResponseEntity.ok(service.updateStudent(regid, dto));
    }

    // Delete Student
    @DeleteMapping("/delete/{regid}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Integer regid) {

        service.deleteStudent(regid);
        return ResponseEntity.ok("Student Deleted Successfully");
    }
    // Get All Castes
@GetMapping("/castes")
public ResponseEntity<List<CasteMaster>> getAllCastes() {

    return ResponseEntity.ok(service.getAllCastes());

}

// Get Sub Castes By Caste
@GetMapping("/subcastes/{casteId}")
public ResponseEntity<List<SubCasteMaster>> getSubCastes(
        @PathVariable Long casteId) {

    return ResponseEntity.ok(service.getSubCastes(casteId));

}
}