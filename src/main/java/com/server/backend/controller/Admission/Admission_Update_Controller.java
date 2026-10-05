package com.server.backend.controller.Admission;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.server.backend.DTO.Admission_Update_DTO;
import com.server.backend.service.Admission.Admission_Update_Service;

@RestController
@RequestMapping("/admission")
public class Admission_Update_Controller {

    private final Admission_Update_Service service;

    public Admission_Update_Controller(
            Admission_Update_Service service) {
        this.service = service;
    }

    @GetMapping("/admission-update")
    public ResponseEntity<Admission_Update_DTO> getAdmission(
            @RequestParam String admissionNumber) {

        try {
            return ResponseEntity.ok(
                    service.getAdmission(admissionNumber));

        } catch (IllegalArgumentException ex) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ex.getMessage());
        }
    }

}
