package com.server.backend.controller.Student;
import org.springframework.web.bind.annotation.CrossOrigin;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.server.backend.dto.AdmissionPhaseDto;
import com.server.backend.service.Student.AdmissionPhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.server.backend.entity.AdmissionPhase;
@CrossOrigin(origins = "http://localhost:5051")
@Tag(name = "Student")
@RestController
@RequestMapping("/api/admission-phase")
public class AdmissionPhaseController {

    @Autowired
    private AdmissionPhaseService admissionPhaseService;

    @PostMapping("/save")
    public ResponseEntity<AdmissionPhaseDto> saveAdmissionPhase(@RequestBody AdmissionPhaseDto dto) {

        AdmissionPhaseDto response = admissionPhaseService.saveAdmissionPhase(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdmissionPhase>> getAllAdmissionPhases() {
        return ResponseEntity.ok(admissionPhaseService.getAllAdmissionPhases());
    }
    @GetMapping("/{year}/{phase}/{itiType}")
public ResponseEntity<AdmissionPhase> getAdmissionPhase(
        @PathVariable String year,
        @PathVariable Integer phase,
        @PathVariable String itiType) {

    return ResponseEntity.ok(
            admissionPhaseService.getAdmissionPhase(year, phase, itiType));
}
@PutMapping("/update/{year}/{phase}/{itiType}")
public ResponseEntity<AdmissionPhaseDto> updateAdmissionPhase(
        @PathVariable String year,
        @PathVariable Integer phase,
        @PathVariable String itiType,
        @RequestBody AdmissionPhaseDto dto) {

    AdmissionPhaseDto response =
            admissionPhaseService.updateAdmissionPhase(year, phase, itiType, dto);

    return ResponseEntity.ok(response);
}
@DeleteMapping("/delete/{year}/{phase}/{itiType}")
public ResponseEntity<String> deleteAdmissionPhase(
        @PathVariable String year,
        @PathVariable Integer phase,
        @PathVariable String itiType) {

    String response =
            admissionPhaseService.deleteAdmissionPhase(year, phase, itiType);

    return ResponseEntity.ok(response);
}
}