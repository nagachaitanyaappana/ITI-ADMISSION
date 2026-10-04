package com.server.backend.controller.Admission;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Call_Letter_DTO;
import com.server.backend.service.Admission.Call_Letter_Service;

@RestController
@RequestMapping("/admission")
public class Call_Letter_Controller {

    private final Call_Letter_Service service;

    public Call_Letter_Controller(
            Call_Letter_Service service) {

        this.service = service;
    }

    @GetMapping("/call-letter")
    public List<Call_Letter_DTO> getCallLetterData(
            @RequestParam String rank,
            @RequestParam String caste) {

        return service.getCallLetterData(
                rank,
                caste);
    }

}
