
package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.admission_process.trade_details_by_short_dto;
import com.server.backend.service.trade_details_by_short_service;

@RestController
@RequestMapping("/admission")
public class trade_details_by_short_controller {

    private final trade_details_by_short_service service;

    public trade_details_by_short_controller(trade_details_by_short_service service) {
        this.service = service;
    }

    @GetMapping("/trade/{tradeShort}")
    public List<trade_details_by_short_dto> getTradeDetailsByShort(@PathVariable String tradeShort) {

        return service.getTradeDetailsByShort(tradeShort);
    }
}