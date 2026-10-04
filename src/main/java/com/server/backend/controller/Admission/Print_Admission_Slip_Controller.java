package com.server.backend.controller.Admission;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.server.backend.DTO.Print_Admission_slip_DTO;
import com.server.backend.service.Admission.Print_Admission_Slip_Service;

@RestController
@RequestMapping("/admission")
public class Print_Admission_Slip_Controller {

    private final Print_Admission_Slip_Service service;

    public Print_Admission_Slip_Controller(
            Print_Admission_Slip_Service service) {
        this.service = service;
    }

    @GetMapping("/admission-slip")
    public ResponseEntity<Print_Admission_slip_DTO> getAdmissionSlip(
            @RequestParam String admissionNumber) {

        try {
            return ResponseEntity.ok(
                    service.getAdmissionSlip(admissionNumber));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}