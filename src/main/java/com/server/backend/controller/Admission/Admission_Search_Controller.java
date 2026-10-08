package com.server.backend.controller.Admission;



import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Admission_Search_DTO;
import com.server.backend.service.Admission.Admission_Search_Service;

@RestController
@RequestMapping("/admission")
public class Admission_Search_Controller {

    private final Admission_Search_Service service;

    public Admission_Search_Controller(
            Admission_Search_Service service) {

        this.service = service;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Admission_Search_DTO>> searchAdmissions(

            @RequestParam(required = false)
            String admissionNumber,

            @RequestParam(required = false)
            String registrationNumber,

            @RequestParam(required = false)
            String name,

            @RequestParam(required = false)
            String fatherName,

            @RequestParam(required = false)
            Long phoneNumber,

            @RequestParam(required = false)
            String gender,

            @RequestParam(required = false)
            String caste,

            @RequestParam(required = false)
            String reservationCategory,

            @RequestParam(required = false)
            String yearOfAdmission,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateOfAdmission,

            @RequestParam(required = false)
            String typeOfAdmission,

            @RequestParam(required = false)
            String currentSem,

            @RequestParam(required = false)
            String districtCode,

            @RequestParam(required = false)
            String itiCode,

            @RequestParam(required = false)
            Integer tradeCode,

            @RequestParam(required = false)
            Integer phase,

            @RequestParam(required = false)
            String soundex,

            @RequestParam(required = false)
            String selectedTable,

            @RequestParam(required = false)
            Boolean includeExamsTable) {

        return ResponseEntity.ok(
                service.searchAdmissions(

                        admissionNumber,
                        registrationNumber,
                        name,
                        fatherName,
                        phoneNumber,
                        gender,
                        caste,
                        reservationCategory,
                        yearOfAdmission,
                        dateOfAdmission,
                        typeOfAdmission,
                        currentSem,
                        districtCode,
                        itiCode,
                        tradeCode,
                        phase,
                        soundex,
                        selectedTable,
                        includeExamsTable
                )
        );
    }

}
