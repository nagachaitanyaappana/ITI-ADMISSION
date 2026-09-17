
package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.admission_process.iti.trades_by_eng_nonengg_dto;
import com.server.backend.service.trades_by_eng_nonengg_service;

@RestController
@RequestMapping("/admission")
public class trades_by_eng_nonengg_controller {

    private final trades_by_eng_nonengg_service service;

    public trades_by_eng_nonengg_controller(trades_by_eng_nonengg_service service) {
        this.service = service;
    }

    @GetMapping("/trades/eng-nonengg/{type}")
    public List<trades_by_eng_nonengg_dto> getTradeNamesByType(@PathVariable String type) {
        return service.getTradeNamesByType(type);
    }
}