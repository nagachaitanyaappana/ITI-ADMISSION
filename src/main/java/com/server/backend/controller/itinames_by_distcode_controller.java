package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.service.itinames_by_distcode_service;

@RestController
@RequestMapping("/admission")
public class itinames_by_distcode_controller {

    private final itinames_by_distcode_service service;

    public itinames_by_distcode_controller(itinames_by_distcode_service service) {
        this.service = service;
    }

    @GetMapping("/itis/{distCode}")
    public List<String> getItiNamesByDistrict(@PathVariable String distCode) {
        return service.getItiNamesByDistrict(distCode);
    }
}