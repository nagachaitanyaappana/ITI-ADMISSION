package com.server.backend.controller.Reports;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.AdmissionReportResponse;
import com.server.backend.DTO.Reports.ApiDashboardResponse;
import com.server.backend.DTO.Reports.ApplicantCountResponse;
import com.server.backend.DTO.Reports.CollegeWiseOpenSeatsResponse;
import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.DTO.Reports.DscFullReportResponse;
import com.server.backend.DTO.Reports.ITIAdmissionsReportResponse;
import com.server.backend.DTO.Reports.ItiTradeDisplayResponse;
import com.server.backend.DTO.Reports.MetadataResponse;
import com.server.backend.DTO.Reports.OpenSeatsAbstractResponse;
import com.server.backend.DTO.Reports.PhaseWiseReportResponse;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse;
import com.server.backend.DTO.Reports.TradeDisplayReportRequest;
import com.server.backend.DTO.Reports.TradeDurationSeatsResponse;
import com.server.backend.service.Reports.ReportService;
import com.server.backend.service.Reports.TradeDisplayReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Report Controller", description = "REST APIs for all Reports")
@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final TradeDisplayReportService tradeDisplayReportService;
    private final ReportService reportService;

    public ReportRestController(TradeDisplayReportService tradeDisplayReportService, ReportService reportService) {
        this.tradeDisplayReportService = tradeDisplayReportService;
        this.reportService = reportService;
    }

    // ========== LEGACY TRADE DISPLAY ==========

    @Operation(summary = "Get district options for dropdown")
    @GetMapping("/trade-display/districts")
    public List<DistrictOptionResponse> getDistrictOptions() {
        return tradeDisplayReportService.getDistrictOptions();
    }

    @Operation(summary = "Get ITI trade display report by district and type")
    @PostMapping("/trade-display")
    public List<ItiTradeDisplayResponse> getTradeDisplayReport(
            @RequestBody TradeDisplayReportRequest request) {
        return tradeDisplayReportService.getTradeDisplayReport(request);
    }

    // ========== METADATA ==========

    @Operation(summary = "Get metadata (years, phases, castes, levels)")
    @GetMapping("/metadata")
    public MetadataResponse getMetadata() {
        return reportService.getMetadata();
    }

    // ========== 1. PHASE WISE ABSTRACT ==========

    @Operation(summary = "Phase Wise Admissions Abstract by year")
    @GetMapping("/phase-wise")
    public List<PhaseWiseReportResponse> getPhaseWiseReport(
            @RequestParam String year) {
        return reportService.getPhaseWiseReport(year);
    }

    // ========== 2. OPEN SEATS ABSTRACT (District wise) ==========

    @Operation(summary = "Open Seats Abstract - District wise")
    @GetMapping("/open-seats")
    public List<OpenSeatsAbstractResponse> getOpenSeatsAbstract(
            @RequestParam String year) {
        return reportService.getOpenSeatsAbstract(year);
    }

    // ========== 3. COLLEGE WISE OPEN SEATS ==========

    @Operation(summary = "College Wise Open Seats by year and district")
    @GetMapping("/open-seats/college")
    public List<CollegeWiseOpenSeatsResponse> getCollegeWiseOpenSeats(
            @RequestParam String year,
            @RequestParam String distCode) {
        return reportService.getCollegeWiseOpenSeats(year, distCode);
    }

    // ========== 4. TRADE DURATION SEATS ABSTRACT ==========

    @Operation(summary = "Trade Duration Seats Abstract")
    @GetMapping("/trade-duration-seats")
    public List<TradeDurationSeatsResponse> getTradeDurationSeats(
            @RequestParam String year,
            @RequestParam String durationMonths,
            @RequestParam String itiType) {
        return reportService.getTradeDurationSeats(year, durationMonths, itiType);
    }

    // ========== 5. ADMISSION REPORT (Trade wise) ==========

    @Operation(summary = "Admission Report - Trade wise (boys/girls)")
    @GetMapping("/admission-report")
    public List<AdmissionReportResponse> getAdmissionReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String pwd) {
        return reportService.getAdmissionReport(year, caste, pwd);
    }

    // ========== 6. APPLICANT COUNT NODAL ==========

    @Operation(summary = "Applicant Count Nodal Report")
    @GetMapping("/applicant-count")
    public ApplicantCountResponse getApplicantCount(
            @RequestParam String year,
            @RequestParam String phase) {
        return reportService.getApplicantCount(year, phase);
    }

    // ========== 7. ITI ADMISSIONS REPORT ==========

    @Operation(summary = "ITI Detailed Admissions Report")
    @GetMapping("/iti-admissions")
    public List<ITIAdmissionsReportResponse> getITIAdmissionsReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String govt,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String gender,
            @RequestParam(required = false, defaultValue = "All") String ncvtScvt,
            @RequestParam(required = false, defaultValue = "All") String limitRows) {
        return reportService.getITIAdmissionsReport(year, distCode, govt, caste, gender, ncvtScvt, limitRows);
    }

    // ========== 8. DSC FULL REPORT ==========

    @Operation(summary = "DSC Full Admission Selection Report")
    @GetMapping("/dsc-full")
    public DscFullReportResponse getDscFullReport(
            @RequestParam String distCode,
            @RequestParam String itiCode,
            @RequestParam String tradeCode,
            @RequestParam String phase,
            @RequestParam String year,
            @RequestParam String modeAdm) {
        return reportService.getDscFullReport(distCode, itiCode, tradeCode, phase, year, modeAdm);
    }

    // ========== 9. API DASHBOARD ==========

    @Operation(summary = "API Dashboard - District wise verification status")
    @GetMapping("/api-dashboard")
    public List<ApiDashboardResponse> getApiDashboard(
            @RequestParam String year) {
        return reportService.getApiDashboard(year);
    }

    // ========== 10. STUDENT COMPLETE DETAILS ==========

    @Operation(summary = "Student Complete Profile Details")
    @GetMapping("/student-details")
    public StudentCompleteDetailsResponse getStudentCompleteDetails(
            @RequestParam(required = false) String regid,
            @RequestParam(required = false) String admNum) {
        return reportService.getStudentCompleteDetails(regid, admNum);
    }
}