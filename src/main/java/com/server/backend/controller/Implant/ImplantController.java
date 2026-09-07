package com.server.backend.controller.Implant;

import java.util.Map;
import java.util.List;
import com.server.backend.DTO.Implant.ImplantReportResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Implant.ImplantCreateRequest;
import com.server.backend.DTO.Implant.ImplantResponse;
import com.server.backend.DTO.Implant.InplantDashboardResponse;
import com.server.backend.DTO.Implant.IndustryMappingRequest;
import com.server.backend.service.Implant.ImplantService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Implant", description = "Implant management APIs")
@RestController
@RequestMapping("/api/implant")
public class ImplantController {

    private final ImplantService implantService;

    public ImplantController(ImplantService implantService) {
        this.implantService = implantService;
    }

    @GetMapping("/overviewdetails")
    public ResponseEntity<InplantDashboardResponse> overviewdetails() {

        return ResponseEntity.ok(
                implantService.getInplantDashboardDetails()
        );
    }

    @GetMapping("/inplantDashboardDetails")
    public ResponseEntity<InplantDashboardResponse> inplantDashboardDetails() {

        return ResponseEntity.ok(
                implantService.getInplantDashboardDetails()
        );
    }


   
    @PostMapping
    public ResponseEntity<ImplantResponse> createImplant(
            @RequestBody ImplantCreateRequest request) {

        ImplantResponse response =
                implantService.createImplant(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<List<ImplantResponse>> getAllImplants() {

        List<ImplantResponse> response =
                implantService.getAllImplants();

        return ResponseEntity.ok(response);
    }

   // Get a specific implant record by its ID
    @GetMapping("/{implantId}")
    public ResponseEntity<ImplantResponse> getImplantById(
            @PathVariable Long implantId) {

        ImplantResponse response =
                implantService.getImplantById(implantId);

        return ResponseEntity.ok(response);
    }
    // Update an existing implant record by its ID
    @PutMapping("/{implantId}")
public ResponseEntity<ImplantResponse> updateImplant(
        @PathVariable Long implantId,
        @RequestBody ImplantCreateRequest request) {

    ImplantResponse response =
            implantService.updateImplant(implantId, request);

    return ResponseEntity.ok(response);
}    
    // Delete an implant record by its ID
    @DeleteMapping("/{implantId}")
    public ResponseEntity<Void> deleteImplant(
        @PathVariable Long implantId) {

    implantService.deleteImplant(implantId);

    return ResponseEntity.noContent().build();
}
    //district login chesina user ki iti code dorikithe, iti code tho industries dorikithe, industries tho trades dorikithe, trades tho report dorikithe, report ni return cheyali
    @GetMapping("/itis")
public ResponseEntity<List<Object[]>> getItis() {
    return ResponseEntity.ok(
            implantService.getItis());
}
 
@GetMapping("/industries")
public ResponseEntity<List<Object[]>> getIndustries(
        @RequestParam Integer itiCode) {

    return ResponseEntity.ok(
            implantService.getIndustries(itiCode));
}
//report endpoint to fetch the report based on itiCode
    // ========== ITI - INDUSTRY MAPPING ==========
    @GetMapping("/mapping/masters")
    public ResponseEntity<Map<String, Object>> getMappingMasters() {
        return ResponseEntity.ok(implantService.getMappingMasters());
    }

    @GetMapping("/mapping")
    public ResponseEntity<List<Map<String, Object>>> getMappings(
            @RequestParam Integer itiCode) {
        return ResponseEntity.ok(implantService.getMappings(itiCode));
    }

    @PostMapping("/mapping")
    public ResponseEntity<Map<String, Object>> saveMapping(
            @RequestParam Integer itiCode,
            @RequestBody IndustryMappingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(implantService.saveMapping(itiCode, request));
    }

    @GetMapping("/mapping/{slno}")
    public ResponseEntity<Map<String, Object>> getMappingBySlno(@PathVariable Long slno) {
        return ResponseEntity.ok(implantService.getMappingBySlno(slno));
    }

    @PutMapping("/mapping/{slno}")
    public ResponseEntity<Map<String, Object>> updateMapping(
            @PathVariable Long slno,
            @RequestBody IndustryMappingRequest request) {
        return ResponseEntity.ok(implantService.updateMapping(slno, request));
    }

    @DeleteMapping("/mapping/{slno}")
    public ResponseEntity<Map<String, Object>> deleteMapping(@PathVariable Long slno) {
        implantService.deleteMapping(slno);
        return ResponseEntity.ok(Map.of("message", "Mapping deleted successfully."));
    }

    // ========== INDUSTRY MASTER (Nodal) ==========
    // NOTE: full CRUD already exists in IndustryMasterController (/api/implant/industry-master)

    @GetMapping("/mapping/districts")
    public ResponseEntity<List<Map<String, Object>>> getMappingDistricts() {
        return ResponseEntity.ok(implantService.getMappingDistricts());
    }

    @GetMapping("/mapping/itis")
    public ResponseEntity<List<Map<String, Object>>> getMappingItis(
            @RequestParam String distCode) {
        return ResponseEntity.ok(implantService.getMappingItis(distCode));
    }

    @GetMapping("/mapping-report")
    public ResponseEntity<List<Map<String, Object>>> getNodalMappingReport() {
        return ResponseEntity.ok(implantService.getNodalMappingReport());
    }

    @GetMapping("/trainees/counts")
    public ResponseEntity<Map<String, Object>> getTraineesCounts() {
        return ResponseEntity.ok(implantService.getTraineesCounts());
    }

    @GetMapping("/trainees")
    public ResponseEntity<List<Map<String, Object>>> getTraineesByType(
            @RequestParam String type) {
        return ResponseEntity.ok(implantService.getTraineesByType(type));
    }

    @GetMapping("/nodal-report")
    public ResponseEntity<List<ImplantReportResponse>> getNodalReport() {
        return ResponseEntity.ok(implantService.getNodalReport());
    }

    @GetMapping("/datewise-report")
    public ResponseEntity<List<ImplantReportResponse>> getDatewiseReport(
            @RequestParam String fromDate, @RequestParam String toDate) {
        return ResponseEntity.ok(implantService.getDatewiseReport(fromDate, toDate));
    }

    @GetMapping("/yearwise-report")
    public ResponseEntity<?> getYearwiseReport(@RequestParam int year, @RequestParam(required = false) String itiType) {
        if (itiType != null && !itiType.isEmpty() && !"M".equals(itiType) && !"A".equals(itiType) && !"L".equals(itiType)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid ITI type. Use M/A/L or leave empty."));
        }
        return ResponseEntity.ok(implantService.getYearwiseReport(year, itiType == null ? "" : itiType));
    }

    @GetMapping("/report")
public ResponseEntity<List<ImplantReportResponse>> getReport(
        @RequestParam String itiCode) {

    return ResponseEntity.ok(
            implantService.getReport(itiCode)
    );
}

    @GetMapping("/district/itis")
    public ResponseEntity<List<Object[]>> getDistrictItis(
            @RequestParam String distCode) {
        return ResponseEntity.ok(implantService.getDistrictItis(distCode));
    }

    @GetMapping("/district/report")
    public ResponseEntity<List<ImplantReportResponse>> getDistrictReport(
            @RequestParam(required = false) String itiCode,
            @RequestParam(required = false) Integer industryId) {
        return ResponseEntity.ok(implantService.getDistrictReport(itiCode, industryId));
    }

    @GetMapping("/master/states")
    public ResponseEntity<List<Map<String, Object>>> getStates() {
        return ResponseEntity.ok(implantService.getStates());
    }

    @GetMapping("/master/districts")
    public ResponseEntity<List<Map<String, Object>>> getDistricts(
            @RequestParam String stateCode) {
        return ResponseEntity.ok(implantService.getDistrictsByState(stateCode));
    }
}
