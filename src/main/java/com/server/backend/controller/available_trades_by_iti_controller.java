package com.server.backend.controller;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.admission_process.available_trades_by_iti_dto;
import com.server.backend.service.available_trades_by_iti_service;

@RestController
@RequestMapping("/admission")
public class available_trades_by_iti_controller {

    private final available_trades_by_iti_service service;

    public available_trades_by_iti_controller(available_trades_by_iti_service service) {
        this.service = service;
    }

    @GetMapping("/iti/{itiCode}/trades")
    public List<available_trades_by_iti_dto> getAvailableTradesByIti(@PathVariable String itiCode) {
        return service.getAvailableTradesByIti(itiCode);
    }
}