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

import com.server.backend.entity.AdmissionTiming;
import com.server.backend.service.AdmissionTimingService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "admission-timings", description = "Admission timing schedule management")
@RestController
@RequestMapping("/admission-timings")
public class AdmissionTimingController {
    private final AdmissionTimingService admissionTimingService;
    public AdmissionTimingController(AdmissionTimingService admissionTimingService) {
        this.admissionTimingService = admissionTimingService;
    }
    @PostMapping
public AdmissionTiming createAdmissionTiming(@RequestBody AdmissionTiming admissionTiming) {
        return admissionTimingService.createAdmissionTiming(admissionTiming);
    }
    @GetMapping
    public List<AdmissionTiming> getAllAdmissionTimings() {
        return admissionTimingService.getAllAdmissionTimings();
    }
    @GetMapping("/{itiCode}/{phase}")
    public AdmissionTiming getById(@PathVariable String itiCode, @PathVariable String phase) {
        return admissionTimingService.getById(itiCode, phase);
    }
    @DeleteMapping("/{itiCode}/{phase}")
    public String delete(@PathVariable String itiCode, @PathVariable String phase) {
        admissionTimingService.delete(itiCode, phase);
        return "Schedule Entry Deleted Successfully";
    }
    @PutMapping("/{itiCode}/{phase}")
    public AdmissionTiming updateAdmissionTiming(@PathVariable String itiCode, @PathVariable String phase, @RequestBody AdmissionTiming updatedAdmissionTiming) {
        return admissionTimingService.updateAdmissionTiming(itiCode, phase, updatedAdmissionTiming);
    }
    
}
