
package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.admission_process.trade_details_by_code_dto;
import com.server.backend.service.trade_details_by_code_service;

@RestController
@RequestMapping("/admission")
public class trade_details_by_code_controller {

    private final trade_details_by_code_service service;

    public trade_details_by_code_controller(trade_details_by_code_service service) {
        this.service = service;
    }

    @GetMapping("/trade/{tradeCode}/details")
    public List<trade_details_by_code_dto> getTradeDetailsByCode(@PathVariable String tradeCode) {

        return service.getTradeDetailsByCode(tradeCode);
    }
}