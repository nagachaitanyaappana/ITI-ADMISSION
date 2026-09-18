package com.server.backend.controller.Admission;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.iti_list_by_district_dto;
import com.server.backend.service.Admission.iti_list_by_district_service;

@Tag(name = "Admission Process")
@RestController
@RequestMapping("/admission")
public class iti_list_by_district_controller {

    private final iti_list_by_district_service service;

    public iti_list_by_district_controller(iti_list_by_district_service service) {
        this.service = service;
    }

    @GetMapping("/district/{distCode}/itis")
    public List<iti_list_by_district_dto> getItisByDistrict(@PathVariable String distCode) {

        return service.getItisByDistrict(distCode);
    }
}