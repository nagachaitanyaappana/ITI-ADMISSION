package com.server.backend.controller.Admission;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.server.backend.DTO.Discharge_Admission_DTO;
import com.server.backend.service.Admission.Discharge_Admission_Service;

@RestController
@RequestMapping("/admission")
public class Discharge_Admission_Controller {

    private final Discharge_Admission_Service service;

    public Discharge_Admission_Controller(
            Discharge_Admission_Service service) {
        this.service = service;
    }

    @GetMapping("/discharge-admission")
    public ResponseEntity<Discharge_Admission_DTO> getAdmission(
            @RequestParam String admissionNumber,
            @RequestParam String year) {

        try {
            return ResponseEntity.ok(
                    service.getAdmission(admissionNumber, year));

        } catch (IllegalArgumentException ex) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ex.getMessage());
        }
    }

}
