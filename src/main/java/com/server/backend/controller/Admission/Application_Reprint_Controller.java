package com.server.backend.controller.Admission;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.server.backend.DTO.Application_Reprint_DTO;
import com.server.backend.service.Admission.Application_Reprint_Service;

@RestController
@RequestMapping("/admission")
public class Application_Reprint_Controller {

    private final Application_Reprint_Service service;

    public Application_Reprint_Controller(
            Application_Reprint_Service service) {

        this.service = service;
    }

    @GetMapping("/application-reprint")
    public ResponseEntity<Application_Reprint_DTO> getApplication(
            @RequestParam Long registrationId) {

        try {

            return ResponseEntity.ok(
                    service.getApplication(registrationId));

        } catch (IllegalArgumentException ex) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ex.getMessage());
        }
    }
}