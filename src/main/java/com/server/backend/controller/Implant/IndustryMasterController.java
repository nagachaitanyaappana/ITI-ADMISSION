package com.server.backend.controller.Implant;

import com.server.backend.DTO.Implant.IndustryMasterRequest;
import com.server.backend.DTO.Implant.IndustryMasterResponse;
import com.server.backend.service.Implant.IndustryMasterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
@Tag(name="Industry Master Controller", description="Controller for managing industry master data")
@RestController
@RequestMapping("/api/implant/industry-master")
public class IndustryMasterController {

    private final IndustryMasterService industryMasterService;

    public IndustryMasterController(
            IndustryMasterService industryMasterService) {

        this.industryMasterService = industryMasterService;
    }

    @PostMapping
    public ResponseEntity<IndustryMasterResponse> createIndustry(
            @RequestBody IndustryMasterRequest request) {

        IndustryMasterResponse response =
                industryMasterService.createIndustry(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping
public ResponseEntity<List<IndustryMasterResponse>> getAllIndustries() {

    List<IndustryMasterResponse> response =
            industryMasterService.getAllIndustries();

    return ResponseEntity.ok(response);
}
@GetMapping("/{industryId}")
public ResponseEntity<IndustryMasterResponse> getIndustryById(
        @PathVariable Long industryId) {

    IndustryMasterResponse response =
            industryMasterService.getIndustryById(industryId);

    return ResponseEntity.ok(response);
}
@PutMapping("/{industryId}")
public ResponseEntity<IndustryMasterResponse> updateIndustry(
        @PathVariable Long industryId,
        @RequestBody IndustryMasterRequest request) {

    IndustryMasterResponse response =
            industryMasterService.updateIndustry(industryId, request);

    return ResponseEntity.ok(response);
}
@DeleteMapping("/{industryId}")
public ResponseEntity<Void> deleteIndustry(
        @PathVariable Long industryId) {

    industryMasterService.deleteIndustry(industryId);

    return ResponseEntity.noContent().build();
}
}