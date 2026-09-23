package com.server.backend.controller.MeritChecklist;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.server.backend.DTO.CurrentUser;
import com.server.backend.DTO.CreateEntryRequest;
import com.server.backend.DTO.UpdateTimingsRequest;
import com.server.backend.DTO.ViewScheduleRequest;
import com.server.backend.entity.AdmissionTiming;
import com.server.backend.service.MeritChecklist.AdmissionTimingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
@Tag(name = "admission-timings", description = "Admission timing schedule management")
@RestController
@RequestMapping("/admission-timings")
@CrossOrigin(origins="http://localhost:5052")
public class AdmissionTimingController {
    private final AdmissionTimingService admissionTimingService;
    public AdmissionTimingController(AdmissionTimingService admissionTimingService) {
        this.admissionTimingService = admissionTimingService;
    }
   @Operation(summary = "Create a new admission timing schedule entry")
    @PostMapping
public AdmissionTiming createAdmissionTiming(@RequestBody AdmissionTiming admissionTiming) {
    return admissionTimingService.createAdmissionTiming(admissionTiming);
    }
   @Operation(summary = "Retrieve all admission timing schedules")
    @GetMapping
    public List<AdmissionTiming> getAllAdmissionTimings() {
        return admissionTimingService.getAllAdmissionTimings();
    }
    @Operation(summary = "Retrieve a specific admission timing schedule by ITI code and phase")
    @GetMapping("/{itiCode}/{phase}")
    public AdmissionTiming getById(@PathVariable String itiCode, @PathVariable String phase) {
        return admissionTimingService.getById(itiCode, phase);
    }
    @Operation(summary = "Delete an admission timing schedule by ITI code and phase")
    @DeleteMapping("/{itiCode}/{phase}")
    public String delete(@PathVariable String itiCode, @PathVariable String phase) {
        admissionTimingService.delete(itiCode, phase);
        return "Schedule Entry Deleted Successfully";
    }
    @Operation(summary = "Update an admission timing schedule by ITI code and phase")
    @PutMapping("/{itiCode}/{phase}")
    public AdmissionTiming updateAdmissionTiming(@PathVariable String itiCode, @PathVariable String phase, @RequestBody AdmissionTiming updatedAdmissionTiming) {
        return admissionTimingService.updateAdmissionTiming(itiCode, phase, updatedAdmissionTiming);
    }
// 2. Add these three endpoints:
@Operation(summary = "Create a new schedule entry")
@PostMapping("/entry")
public ResponseEntity<Map<String, Object>> createScheduleEntry(@RequestBody CreateEntryRequest req) {
    // TODO: Replace this mock user with your actual Spring Security context user retrieval
    CurrentUser user = new CurrentUser("ITI001", "24", "12345", "3"); 
    
    Map<String, Object> result = admissionTimingService.createScheduleEntry(req, user);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
}

@Operation(summary = "Add schedule timings")
@PutMapping("/timings")
public ResponseEntity<Map<String, Object>> addScheduleTimings(@Valid @RequestBody UpdateTimingsRequest req) {
    // TODO: Replace this mock user with your actual Spring Security context user retrieval
    CurrentUser user = new CurrentUser("ITI001", "24", "12345", "3"); 
    
    Map<String, Object> result = admissionTimingService.addScheduleTimings(req, user);
    return new ResponseEntity<>(result, HttpStatus.OK);
}
@Operation(summary = "View schedule timings")
@PostMapping("/view")
public ResponseEntity<Map<String, Object>> viewScheduleTimings(@RequestBody ViewScheduleRequest req) {
    // TODO: Replace this mock user with your actual Spring Security context user retrieval
    CurrentUser user = new CurrentUser("ITI001", "24", "12345", "3"); 
    
    Map<String, Object> result = admissionTimingService.viewScheduleTimings(req, user);
    return new ResponseEntity<>(result, HttpStatus.OK);
}

// 3. Add an Exception Handler to catch scheduling/overlap validation errors and send them to the frontend
@ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
public ResponseEntity<Map<String, Object>> handleSchedulingErrors(Exception ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("error", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
}

}
