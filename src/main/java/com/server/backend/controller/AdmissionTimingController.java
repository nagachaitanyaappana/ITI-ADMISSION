package com.server.backend.controller;
import org.springframework.web.bind.annotation.*;
import com.server.backend.entity.AdmissionTiming;
import com.server.backend.service.AdmissionTimingService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @GetMapping("/{itiCode}")
    public AdmissionTiming getById(@PathVariable String itiCode) {
        return admissionTimingService.getById(itiCode);
    }
    @DeleteMapping("/{itiCode}")
    public String delete(@PathVariable String itiCode) {
        admissionTimingService.delete(itiCode);
        return "Schedule Entry Deleted Successfully";
    }
    @PutMapping("/{itiCode}")
    public AdmissionTiming updateAdmissionTiming(@PathVariable String itiCode, @RequestBody AdmissionTiming updatedAdmissionTiming) {
        return admissionTimingService.updateAdmissionTiming(itiCode, updatedAdmissionTiming);
    }
    
}
