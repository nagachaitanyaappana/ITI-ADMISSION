package com.server.backend.controller.Placements;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Placements.PlacementsDistinctItiResponse;
import com.server.backend.DTO.Placements.PlacementsGroupedResponse;
import com.server.backend.DTO.Placements.PlacementsOverviewResponse;
import com.server.backend.service.Placements.PlacementsService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/placements")
public class PlacementsController {

    private final PlacementsService placementsService;

    public PlacementsController(PlacementsService placementsService) {
        this.placementsService = placementsService;
    }

    @GetMapping("/overviewdetails")
    public ResponseEntity<PlacementsOverviewResponse> overviewdetails() {
        return ResponseEntity.ok(placementsService.getOverviewDetails());
    }

    @GetMapping("/countPlacementsGroupedByPtype")
    public ResponseEntity<PlacementsGroupedResponse> countPlacementsGroupedByPtype() {
        return ResponseEntity.ok(placementsService.getCountPlacementsGroupedByPtype());
    }

    @GetMapping("/getdDistinctItiCodesByPtype")
    public ResponseEntity<PlacementsDistinctItiResponse> getdDistinctItiCodesByPtype() {
        return ResponseEntity.ok(placementsService.getDistinctItiCodesByPtype());
    }

    @GetMapping("/schedulewise/districts")
    public ResponseEntity<List<Map<String, Object>>> getSchedulewiseDistricts(@RequestParam String year) {
        return ResponseEntity.ok(placementsService.getSchedulewiseDistrictData(year));
    }

    @GetMapping("/schedulewise/itis")
    public ResponseEntity<List<Map<String, Object>>> getSchedulewiseItis(
            @RequestParam String year, @RequestParam String distCode) {
        return ResponseEntity.ok(placementsService.getSchedulewiseItiData(year, distCode));
    }

    @GetMapping("/schedulewise/placements")
    public ResponseEntity<List<Map<String, Object>>> getSchedulewisePlacements(@RequestParam String plcmtId) {
        return ResponseEntity.ok(placementsService.getSchedulewisePlacementDetails(plcmtId));
    }

    @GetMapping("/datewise/schedules")
    public ResponseEntity<List<Map<String, Object>>> getDatewiseSchedules(
            @RequestParam String fromDate, @RequestParam String toDate,
            @RequestParam String ptype) {
        return ResponseEntity.ok(placementsService.getDatewiseScheduleData(fromDate, toDate, ptype));
    }

    @GetMapping("/state/report")
    public ResponseEntity<List<Map<String, Object>>> getStateReport() {
        return ResponseEntity.ok(placementsService.getStatePlacementReport());
    }

    @GetMapping("/yearwise/report")
    public ResponseEntity<List<Map<String, Object>>> getYearwiseReport() {
        return ResponseEntity.ok(placementsService.getYearwisePlacementReport());
    }

    @GetMapping("/yearwise/details")
    public ResponseEntity<List<Map<String, Object>>> getYearwiseDetails(@RequestParam String year) {
        return ResponseEntity.ok(placementsService.getYearwisePlacementDetails(year));
    }

    @GetMapping("/ssdp/years")
    public ResponseEntity<List<String>> getSsdpYears() {
        return ResponseEntity.ok(placementsService.getDistinctAdmissionYears());
    }

    @GetMapping("/ssdp/report")
    public ResponseEntity<List<Map<String, Object>>> getSsdpReport(@RequestParam String year) {
        return ResponseEntity.ok(placementsService.getStateSkillDevelopmentPlanReport(year));
    }

    @GetMapping("/datadetails/report")
    public ResponseEntity<List<Map<String, Object>>> getDataDetailsReport(
            @RequestParam String year, @RequestParam String itiType) {
        return ResponseEntity.ok(placementsService.getPlacementDataDetailsReport(year, itiType));
    }

    @GetMapping("/state/district")
    public ResponseEntity<List<Map<String, Object>>> getDistrictReport(@RequestParam String distCode) {
        return ResponseEntity.ok(placementsService.getDistrictPlacementReport(distCode));
    }

    // ===== Schedule Entry (district) =====
    @GetMapping("/schedule/itis")
    public ResponseEntity<List<Map<String, Object>>> getScheduleItis(@RequestParam String distCode) {
        return ResponseEntity.ok(placementsService.getDistrictScheduleItis(distCode));
    }

    @GetMapping("/schedule/list")
    public ResponseEntity<List<Map<String, Object>>> getSchedules(@RequestParam String distCode) {
        return ResponseEntity.ok(placementsService.getDistrictSchedules(distCode));
    }

    @PostMapping("/schedule")
    public ResponseEntity<Map<String, Object>> createSchedule(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok(placementsService.createSchedule(req));
    }

    // ===== Dist Report (district) =====
    @GetMapping("/district/years")
    public ResponseEntity<List<String>> getDistrictYears(@RequestParam String distCode) {
        return ResponseEntity.ok(placementsService.getDistrictPlacementYears(distCode));
    }

    @GetMapping("/district/itis")
    public ResponseEntity<List<Map<String, Object>>> getDistrictItis(@RequestParam String distCode) {
        return ResponseEntity.ok(placementsService.getDistrictItis(distCode));
    }

    @GetMapping("/district/report")
    public ResponseEntity<List<Map<String, Object>>> getDistrictReportData(
            @RequestParam String distCode, @RequestParam String ptype,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String itiCode) {
        return ResponseEntity.ok(placementsService.getDistrictPlacementReport(distCode, ptype, year, itiCode));
    }

    // ===== Placements Entry (ITI) =====
    @GetMapping("/candidate")
    public ResponseEntity<Map<String, Object>> getCandidate(@RequestParam String admNum, @RequestParam String itiCode) {
        return ResponseEntity.ok(placementsService.getCandidateByAdmNum(admNum, itiCode));
    }

    @GetMapping("/candidate/byName")
    public ResponseEntity<List<Map<String, Object>>> findCandidates(
            @RequestParam String name, @RequestParam String itiCode) {
        return ResponseEntity.ok(placementsService.findCandidatesByName(name, itiCode));
    }

    @GetMapping("/candidate/placements")
    public ResponseEntity<List<Map<String, Object>>> getCandidatePlacements(
            @RequestParam String admNum, @RequestParam String itiCode) {
        return ResponseEntity.ok(placementsService.getCandidatePlacements(admNum, itiCode));
    }

    @GetMapping("/schedule/itischedules")
    public ResponseEntity<List<Map<String, Object>>> getItiSchedules(@RequestParam String itiCode) {
        return ResponseEntity.ok(placementsService.getItiSchedules(itiCode));
    }

    @GetMapping("/master/trades")
    public ResponseEntity<List<Map<String, Object>>> getMasterTrades() {
        return ResponseEntity.ok(placementsService.getMasterTrades());
    }

    @GetMapping("/master/states")
    public ResponseEntity<List<Map<String, Object>>> getMasterStates() {
        return ResponseEntity.ok(placementsService.getMasterStates());
    }

    @GetMapping("/master/districts")
    public ResponseEntity<List<Map<String, Object>>> getMasterDistricts() {
        return ResponseEntity.ok(placementsService.getMasterDistricts());
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPlacement(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok(placementsService.createPlacement(req));
    }

    // ===== Placements ITI Report (ITI) =====
    @GetMapping("/iti/years")
    public ResponseEntity<List<String>> getItiYears(@RequestParam String itiCode) {
        return ResponseEntity.ok(placementsService.getItiPlacementYears(itiCode));
    }

    @GetMapping("/iti/report")
    public ResponseEntity<List<Map<String, Object>>> getItiReport(
            @RequestParam String itiCode, @RequestParam String ptype,
            @RequestParam(required = false) String year) {
        return ResponseEntity.ok(placementsService.getItiPlacementReport(itiCode, ptype, year));
    }
}
