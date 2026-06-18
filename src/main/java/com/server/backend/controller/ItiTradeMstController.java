package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.server.backend.DTO.Institute.ItiTradeMstDto;
import com.server.backend.entity.Iti_trade_mst;
import com.server.backend.service.ItiTradeMstService;

@RestController
@RequestMapping("/api/iti/trades")
public class ItiTradeMstController {

    private final ItiTradeMstService service;

    public ItiTradeMstController(
            ItiTradeMstService service) {

        this.service = service;
    }

    @GetMapping
    public List<Iti_trade_mst> getAllTrades() {

        return service.getAllTrades();
    }

    @GetMapping("/{tradeShort}")
    public Iti_trade_mst getTradeById(
            @PathVariable String tradeShort) {

        return service.getTradeById(tradeShort);
    }

    @PostMapping
    public Iti_trade_mst createTrade(
            @RequestBody ItiTradeMstDto dto) {

        return service.createTrade(dto);
    }

    @PutMapping("/{tradeShort}")
    public Iti_trade_mst updateTrade(
            @PathVariable String tradeShort,
            @RequestBody ItiTradeMstDto dto) {

        return service.updateTrade(tradeShort, dto);
    }

    @DeleteMapping("/{tradeShort}")
    public String deleteTrade(
            @PathVariable String tradeShort) {

        service.deleteTrade(tradeShort);

        return "Trade Deleted Successfully";
    }
}