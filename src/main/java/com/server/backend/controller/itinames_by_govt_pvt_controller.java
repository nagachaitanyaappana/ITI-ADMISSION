package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.service.itinames_by_govt_pvt_service;

@RestController
@RequestMapping("/admission")
public class itinames_by_govt_pvt_controller {

   
    private itinames_by_govt_pvt_service service;

    @GetMapping("/itis/govt-pvt/{govt}")
    public List<String> getItiNamesByGovtPvt(@PathVariable String govt) {
        return service.getItiNamesByGovtPvt(govt);
    }
}