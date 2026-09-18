
package com.server.backend.controller.Admission;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.admission_process.seat_details_by_iti_dto;
import com.server.backend.service.Admission.seat_details_by_iti_service;

@Tag(name = "Admission Process")
@RestController
@RequestMapping("/admission")
public class seat_details_by_iti_controller {

    private final seat_details_by_iti_service service;

    public seat_details_by_iti_controller(seat_details_by_iti_service service) {
        this.service = service;
    }

    @GetMapping("/iti/{itiCode}/seat-details")
    public List<seat_details_by_iti_dto> getSeatDetailsByIti(@PathVariable String itiCode) {

        return service.getSeatDetailsByIti(itiCode);
    }
}