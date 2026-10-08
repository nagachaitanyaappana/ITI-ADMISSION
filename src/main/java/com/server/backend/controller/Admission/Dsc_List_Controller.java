package com.server.backend.controller.Admission;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Dsc_List_DTO;
import com.server.backend.service.Admission.Dsc_List_Service;

@RestController
@RequestMapping("/admission")
public class Dsc_List_Controller {

    private final Dsc_List_Service service;

    public Dsc_List_Controller(Dsc_List_Service service) {
        this.service = service;
    }

    @GetMapping("/dsc-list")
    public ResponseEntity<List<Dsc_List_DTO>> getDscList(

            @RequestParam String itiCode,

            @RequestParam Integer tradeCode,

            @RequestParam Integer phase,

            @RequestParam String year,

            @RequestParam String admissionLevel) {

        return ResponseEntity.ok(
                service.getDscList(
                        itiCode,
                        tradeCode,
                        phase,
                        year,
                        admissionLevel
                )
        );
    }

}
