package com.server.backend.controller.Admission;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.server.backend.entity.Iti;
import com.server.backend.service.Admission.itinames_by_govt_pvt_service;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admission Process")
@RestController
@RequestMapping("/api/iti")
@CrossOrigin(origins = "*")
public class itinames_by_govt_pvt_controller {

    private final itinames_by_govt_pvt_service service;

    public itinames_by_govt_pvt_controller(itinames_by_govt_pvt_service service) {
        this.service = service;
    }

    @GetMapping("/by-govt-pvt/{govt}")
    public List<Iti> getItiNamesByGovt(@PathVariable String govt) {
        return service.getItiNamesByGovt(govt);
    }
}
