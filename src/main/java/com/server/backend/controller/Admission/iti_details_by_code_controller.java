
package com.server.backend.controller.Admission;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.admission_process.iti_details_by_code_dto;
import com.server.backend.service.Admission.iti_details_by_code_service;

@Tag(name = "Admission Process")
@RestController
@RequestMapping("/admission")
public class iti_details_by_code_controller {

    private final iti_details_by_code_service service;

    public iti_details_by_code_controller(iti_details_by_code_service service) {
        this.service = service;
    }

    @GetMapping("/iti/{itiCode}")
    public List<iti_details_by_code_dto> getItiDetailsByCode(@PathVariable String itiCode) {
        return service.getItiDetailsByCode(itiCode);
    }
}