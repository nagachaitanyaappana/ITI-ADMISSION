package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.service.itinames_by_distcode_service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "iti-names", description = "ITI names retrieval operations")
@RestController
@RequestMapping("/admission")
public class itinames_by_distcode_controller {

    private final itinames_by_distcode_service service;

    public itinames_by_distcode_controller(itinames_by_distcode_service service) {
        this.service = service;
    }
@Operation(summary = "Get ITI Names by District Code", description = "Retrieve a list of ITI names based on the provided district code")
    @GetMapping("/itis/{distCode}")
    public List<String> getItiNamesByDistrict(@PathVariable String distCode) {
        return service.getItiNamesByDistrict(distCode);
    }
}