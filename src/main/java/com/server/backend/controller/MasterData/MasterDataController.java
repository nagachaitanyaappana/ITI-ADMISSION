package com.server.backend.controller.MasterData;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.MasterData.DashBoardDataResponse;
import com.server.backend.DTO.MasterData.ItiDetailResponse;
import com.server.backend.DTO.MasterData.ItiPercentStatsResponse;
import com.server.backend.service.MasterData.MasterDataService;

/**
 * Placement-portal dashboard endpoints consumed by placementDashboard.jsp.
 *
 * Mapped at /masterdata (no /api prefix) to match the production ITI placements
 * source paths that the ported JSP calls: baseUrl + "masterdata/...".
 *
 * Data comes from the placements-side schemas (public2 / see MasterDataServiceImpl).
 */
@Tag(name = "Placements & Industry")
@RestController
@RequestMapping("/masterdata")
public class MasterDataController {

    private final MasterDataService masterDataService;

    public MasterDataController(MasterDataService masterDataService) {
        this.masterDataService = masterDataService;
    }

    @GetMapping("/dashBoardData")
    public ResponseEntity<DashBoardDataResponse> dashBoardData(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) Integer phase) {
        return ResponseEntity.ok(masterDataService.getDashBoardData(year, phase));
    }

    @GetMapping("/getAbove20PercentItisStats")
    public ResponseEntity<ItiPercentStatsResponse> getAbove20PercentItisStats(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) Integer phase) {
        return ResponseEntity.ok(masterDataService.getAbove20PercentItisStats(year, phase));
    }

    @GetMapping("/getBelow20PercentItisStats")
    public ResponseEntity<ItiPercentStatsResponse> getBelow20PercentItisStats(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) Integer phase) {
        return ResponseEntity.ok(masterDataService.getBelow20PercentItisStats(year, phase));
    }

    @GetMapping("/getAbove20PercentItis")
    public ResponseEntity<List<ItiDetailResponse>> getAbove20PercentItis(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) Integer phase) {
        return ResponseEntity.ok(masterDataService.getAbove20PercentItis(year, phase));
    }

    @GetMapping("/getBelow20PercentItis")
    public ResponseEntity<List<ItiDetailResponse>> getBelow20PercentItis(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) Integer phase) {
        return ResponseEntity.ok(masterDataService.getBelow20PercentItis(year, phase));
    }
}
