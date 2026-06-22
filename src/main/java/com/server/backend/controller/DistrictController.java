package com.server.backend.controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.Repository.DistrictMasterRepository;



@RestController

@RequestMapping("/api/districts")
public class DistrictController {

    private final DistrictMasterRepository districtRepo;

    public DistrictController(DistrictMasterRepository districtRepo) {
        this.districtRepo = districtRepo;
    }

    @GetMapping
    public List<DistrictOptionResponse> getDistricts() {

        return districtRepo.findDistrictOptions();
    }
}