package com.server.backend.controller.Admission;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.server.backend.DTO.Admission_Image_Upload_DTO;
import com.server.backend.service.Admission.Admission_Image_Upload_Service;

@RestController
@RequestMapping("/admission")
public class Admission_Image_Upload_Controller {

    private final Admission_Image_Upload_Service service;

    public Admission_Image_Upload_Controller(
            Admission_Image_Upload_Service service) {

        this.service = service;
    }

    @GetMapping("/admission-image-upload")
    public ResponseEntity<Admission_Image_Upload_DTO> verifyAdmission(
            @RequestParam String admissionNumber,
            @RequestParam String sscHallticketNumber) {

        try {

            return ResponseEntity.ok(
                    service.verifyAdmission(
                            admissionNumber,
                            sscHallticketNumber));

        } catch (IllegalArgumentException ex) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ex.getMessage());
        }
    }

}
