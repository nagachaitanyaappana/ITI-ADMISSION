package com.server.backend.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.trades_by_tradecode_dto;
import com.server.backend.service.trades_by_tradecode_service;

@RestController
@RequestMapping("/admission")
public class trades_by_tradecode_controller {

    
    private trades_by_tradecode_service service;

    @GetMapping("/trade/{tradeCode}")
    public trades_by_tradecode_dto getTradeDetails(@PathVariable Integer tradeCode) {
        return service.getTradeDetails(tradeCode);
    }
}