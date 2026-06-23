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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Trade Controller", description = "Manage ITI trade master data")
@RestController
@RequestMapping("/api/trades")
public class ItiTradeMstController {

    private final ItiTradeMstService service;

    public ItiTradeMstController(
            ItiTradeMstService service) {

        this.service = service;
    }

    @Operation(summary = "List all trades")
    @GetMapping
    public List<Iti_trade_mst> getAllTrades() {

        return service.getAllTrades();
    }

    @Operation(summary = "Get a trade by short code")
    @GetMapping("/{tradeShort}")
    public Iti_trade_mst getTradeById(
            @PathVariable String tradeShort) {

        return service.getTradeById(tradeShort);
    }

    @Operation(summary = "Create a new trade")
    @PostMapping
    public Iti_trade_mst createTrade(
            @RequestBody ItiTradeMstDto dto) {

        return service.createTrade(dto);
    }

    @Operation(summary = "Update an existing trade")
    @PutMapping("/{tradeShort}")
    public Iti_trade_mst updateTrade(
            @PathVariable String tradeShort,
            @RequestBody ItiTradeMstDto dto) {

        return service.updateTrade(tradeShort, dto);
    }

    @Operation(summary = "Delete a trade")
    @DeleteMapping("/{tradeShort}")
    public String deleteTrade(
            @PathVariable String tradeShort) {

        service.deleteTrade(tradeShort);

        return "Trade Deleted Successfully";
    }
}