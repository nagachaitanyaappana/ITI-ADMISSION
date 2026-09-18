package com.server.backend.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import com.server.backend.entity.ITI.Iti_trade_mst;
import com.server.backend.service.trdnms_by_minqual_service;

@Tag(name = "Admission Process")
@RestController
@RequestMapping("/api/trades")
@CrossOrigin(origins = "*")
public class trdnms_by_minqual_controller {

    private final trdnms_by_minqual_service service;

    public trdnms_by_minqual_controller(trdnms_by_minqual_service service) {
        this.service = service;
    }

    @GetMapping("/by-minqual/{minQual}")
    public List<Iti_trade_mst> getTradeNamesByMinQual(@PathVariable String minQual) {
        return service.getTradeNamesByMinQual(minQual);
    }
}
