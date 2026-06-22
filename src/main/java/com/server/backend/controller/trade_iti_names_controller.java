package com.server.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.server.backend.DTO.admission_process.iti.trade_iti_names_dto;
import com.server.backend.service.trade_iti_names_service;

@RestController
@RequestMapping("/admission")
public class trade_iti_names_controller {

    private final trade_iti_names_service service;

    
    public trade_iti_names_controller(trade_iti_names_service service) {
        this.service = service;
    }

    @GetMapping("/master-data")
    public trade_iti_names_dto getMasterData() {
        return service.getTradesNamesData();
    }
}