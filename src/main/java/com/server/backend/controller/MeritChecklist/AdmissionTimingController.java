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
import org.springframework.web.bind.annotation.RequestHeader;
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
    // 2. These three endpoints resolve the caller from request headers, the same convention
    //    MeritListController uses. They previously hardcoded ("ITI001", "24", "12345", "3"),
    //    so every schedule row was attributed to one fixed ITI regardless of who was logged in.
    @Operation(summary = "Create a new schedule entry")
    @PostMapping("/entry")
    public ResponseEntity<Map<String, Object>> createScheduleEntry(
            @RequestHeader(name = "X-Iti-Code", required = false) String itiCode,
            @RequestHeader(name = "X-Dist-Code", required = false) String distCode,
            @RequestHeader(name = "X-Ins-Code", required = false) String insCode,
            @RequestHeader(name = "X-Role-Id", required = false) String roleId,
            @RequestBody CreateEntryRequest req) {
        CurrentUser user = currentUser(itiCode, distCode, insCode, roleId);
        Map<String, Object> result = admissionTimingService.createScheduleEntry(req, user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Add schedule timings")
    @PutMapping("/timings")
    public ResponseEntity<Map<String, Object>> addScheduleTimings(
            @RequestHeader(name = "X-Iti-Code", required = false) String itiCode,
            @RequestHeader(name = "X-Dist-Code", required = false) String distCode,
            @RequestHeader(name = "X-Ins-Code", required = false) String insCode,
            @RequestHeader(name = "X-Role-Id", required = false) String roleId,
            @Valid @RequestBody UpdateTimingsRequest req) {
        CurrentUser user = currentUser(itiCode, distCode, insCode, roleId);
        Map<String, Object> result = admissionTimingService.addScheduleTimings(req, user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "View schedule timings")
    @PostMapping("/view")
    public ResponseEntity<Map<String, Object>> viewScheduleTimings(
            @RequestHeader(name = "X-Iti-Code", required = false) String itiCode,
            @RequestHeader(name = "X-Dist-Code", required = false) String distCode,
            @RequestHeader(name = "X-Ins-Code", required = false) String insCode,
            @RequestHeader(name = "X-Role-Id", required = false) String roleId,
            @RequestBody ViewScheduleRequest req) {
        CurrentUser user = currentUser(itiCode, distCode, insCode, roleId);
        Map<String, Object> result = admissionTimingService.viewScheduleTimings(req, user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Builds the calling user from request headers. Fails loudly when no institution code is
     * present, instead of silently writing a schedule against a placeholder ITI.
     */
    private CurrentUser currentUser(String itiCode, String distCode, String insCode, String roleId) {
        if (isBlank(insCode) && isBlank(itiCode) && isBlank(distCode)) {
            throw new IllegalArgumentException(
                    "Missing institution code: send X-Ins-Code (or X-Iti-Code / X-Dist-Code)"
                            + " to create or view a schedule");
        }
        String ins = !isBlank(insCode) ? insCode : (!isBlank(distCode) ? distCode : itiCode);
        return new CurrentUser(itiCode, distCode, ins, isBlank(roleId) ? "3" : roleId);
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
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
