package com.server.backend.controller.Admission;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Schedule_Entry_DTO;
import com.server.backend.service.Admission.Schedule_Entry_Service;

@RestController
@RequestMapping("/admission")
public class Schedule_Entry_Controller {

    private final Schedule_Entry_Service service;

    public Schedule_Entry_Controller(
            Schedule_Entry_Service service) {

        this.service = service;
    }

    @GetMapping("/schedule-entry")
    public List<Schedule_Entry_DTO> getScheduleEntries(

            @RequestParam String qualification,

            @RequestParam String caste,

            @RequestParam String phase,

            @RequestParam String year) {

        return service.getScheduleEntries(
                qualification,
                caste,
                phase,
                year);
    }
}