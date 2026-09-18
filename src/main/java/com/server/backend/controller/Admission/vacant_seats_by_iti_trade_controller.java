
package com.server.backend.controller.Admission;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.admission_process.iti.vacant_seats_by_iti_trade_dto;
import com.server.backend.service.Admission.vacant_seats_by_iti_trade_service;

@Tag(name = "Admission Process")
@RestController
@RequestMapping("/admission")
public class vacant_seats_by_iti_trade_controller {

    private final vacant_seats_by_iti_trade_service service;

    public vacant_seats_by_iti_trade_controller(vacant_seats_by_iti_trade_service service) {
        this.service = service;
    }

    @GetMapping("/vacant-seats/{itiCode}/{tradeShort}")
    public List<vacant_seats_by_iti_trade_dto> getVacantSeats(
            @PathVariable String itiCode,
            @PathVariable String tradeShort) {

        return service.getVacantSeats(itiCode, tradeShort);
    }
}