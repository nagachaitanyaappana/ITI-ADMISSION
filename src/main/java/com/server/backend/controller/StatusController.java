package com.server.backend.controller;
import com.server.backend.service.AdmissionTimingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Map;
@Tag(name = "Merit & Checklist")
@RestController
@RequestMapping("/api/status")
@CrossOrigin(origins="http://localhost:5052")
public class StatusController {
    private final AdmissionTimingService admissionTimingService;
    public StatusController(AdmissionTimingService admissionTimingService) {
        this.admissionTimingService = admissionTimingService;
    }
    @PostMapping
    public Map<String,Object>getStatus(){
        return admissionTimingService.getCurrentStatus();
    }
    

}
