package com.server.backend.controller.Reports;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.AdmissionReportResponse;
import com.server.backend.DTO.Reports.AllResourceRoleResponse;
import com.server.backend.DTO.Reports.ApiDashboardResponse;
import com.server.backend.DTO.Reports.ApplicantMobileAddressResponse;
import com.server.backend.DTO.Reports.ApplicantReportResponse;
import com.server.backend.DTO.Reports.CasteWiseAdmissionsResponse;
import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.DTO.Reports.DistrictScheduleResponse;
import com.server.backend.DTO.Reports.DistrictWiseApplicationCountResponse;
import com.server.backend.DTO.Reports.DscFullReportResponse;
import com.server.backend.DTO.Reports.GovtPvtSeatsAbstractResponse;
import com.server.backend.DTO.Reports.ITIAdmissionsReportResponse;
import com.server.backend.DTO.Reports.ItiWiseStatusResponse;
import com.server.backend.DTO.Reports.OpenSeatsAbstractResponse;
import com.server.backend.DTO.Reports.PhaseWiseReportResponse;
import com.server.backend.DTO.Reports.ShiftUnitResponse;
import com.server.backend.DTO.Reports.StateDashboardResponse;
import com.server.backend.DTO.Reports.StrengthFilledSeatsResponse;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse;
import com.server.backend.DTO.Reports.TodayScheduleResponse;
import com.server.backend.DTO.Reports.TradeDurationSeatsResponse;
import com.server.backend.DTO.Reports.TradeWiseReportResponse;
import com.server.backend.DTO.Reports.TradeWiseVacantResponse;
import com.server.backend.DTO.Reports.VerifiedApplicationCountResponse;
import com.server.backend.service.Reports.ReportService;
import com.server.backend.service.Reports.TradeDisplayReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "reports", description = "REST APIs for generating various reports including admissions, applicants, trade-wise analysis, and district-wise statistics")
@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final TradeDisplayReportService tradeDisplayReportService;
    private final ReportService reportService;

    public ReportRestController(TradeDisplayReportService tradeDisplayReportService, ReportService reportService) {
        this.tradeDisplayReportService = tradeDisplayReportService;
        this.reportService = reportService;
    }

    // ========== 1 - API Dashboard (ITI) ==========
    @Operation(summary = "1 - API Dashboard (ITI)")
    @GetMapping("/iti-wise-status")
    public List<ItiWiseStatusResponse> getItiWiseStatus(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getItiWiseStatus(year, distCode, itiCode, page, safeSize);
    }

    // ========== 2 - Applicant Report ==========
    @Operation(summary = "2 - Applicant Report")
    @GetMapping("/applicant-report-by-phase")
    public List<ApplicantReportResponse> getApplicantReportByPhase(
            @RequestParam String phase,
            @RequestParam(required = false) String year,
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getApplicantReportByPhase(phase, year, itiCode, distCode, page, safeSize);
    }

    // ========== 3 - Admission Report (ITI) ==========
    @Operation(summary = "3 - Admission Report (ITI)")
    @GetMapping("/admission-report-iti")
    public List<AdmissionReportResponse> getAdmissionReportIti(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String pwd) {
        return reportService.getAdmissionReport(year, caste, pwd);
    }

    // ========== 4 - DSC List ==========
    @Operation(summary = "4 - DSC List")
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

    // ========== 5 - Caste Wise Admissions Abstract ==========
    @Operation(summary = "5 - Caste Wise Admissions Abstract")
    @GetMapping("/caste-wise-admissions")
    public List<CasteWiseAdmissionsResponse> getCasteWiseAdmissions(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String govt,
            @RequestParam(required = false, defaultValue = "All") String phase,
            @RequestParam(required = false, defaultValue = "All") String gender) {
        return reportService.getCasteWiseAdmissions(year, distCode, govt, phase, gender);
    }

    // ========== 6 - Applicant Address With Mobile ==========
    @Operation(summary = "6 - Applicant Address With Mobile")
    @GetMapping("/applicant-mobile-address")
    public List<ApplicantMobileAddressResponse> getApplicantMobileAddress(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getApplicantMobileAddress(year, distCode, page, safeSize);
    }

    // ========== 7 - API Dashboard (District) ==========
    @Operation(summary = "7 - API Dashboard (District)")
    @GetMapping("/api-dashboard")
    public List<ApiDashboardResponse> getApiDashboard(
            @RequestParam String year) {
        return reportService.getApiDashboard(year);
    }

    // ========== 8 - Verification Report (District) ==========
    @Operation(summary = "8 - Verification Report (District)")
    @GetMapping("/verified-application-count")
    public List<VerifiedApplicationCountResponse> getVerifiedApplicationCount(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getVerifiedApplicationCount(year, distCode);
    }

    // ========== 9 - State Dashboard ==========
    @Operation(summary = "9 - State Dashboard")
    @GetMapping("/state-dashboard")
    public List<StateDashboardResponse> getStateDashboard(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String govt) {
        return reportService.getStateDashboard(year, govt);
    }

    // ========== 10 - Phase Wise Admissions Details ==========
    @Operation(summary = "10 - Phase Wise Admissions Details")
    @GetMapping("/phase-wise")
    public List<PhaseWiseReportResponse> getPhaseWiseReport(
            @RequestParam String year) {
        return reportService.getPhaseWiseReport(year);
    }

    // ========== 11 - Today Schedule ITIs ==========
    @Operation(summary = "11 - Today Schedule ITIs")
    @GetMapping("/today-schedule")
    public List<TodayScheduleResponse> getTodaySchedule() {
        return reportService.getTodaySchedule();
    }

    // ========== 12 - Trade Wise Report ==========
    @Operation(summary = "12 - Trade Wise Report")
    @GetMapping("/trade-wise-report")
    public List<TradeWiseReportResponse> getTradeWiseReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiType) {
        return reportService.getTradeWiseReport(year, distCode, itiType);
    }

    // ========== 13 - Applicant Report Dist Wise ==========
    @Operation(summary = "13 - Applicant Report Dist Wise")
    @GetMapping("/applicant-report-state-wise")
    public List<ApplicantReportResponse> getApplicantReportStateWise(
            @RequestParam String phase,
            @RequestParam(required = false) String year,
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getApplicantReportByPhase(phase, year, itiCode, distCode, page, safeSize);
    }

    // ========== 14 - DIST/ITI/Trade Wise Seats Abstract ==========
    @Operation(summary = "14 - DIST/ITI/Trade Wise Seats Abstract")
    @GetMapping("/open-seats")
    public List<OpenSeatsAbstractResponse> getOpenSeatsAbstract(
            @RequestParam String year) {
        return reportService.getOpenSeatsAbstract(year);
    }

    // ========== 15 - Duration Wise Trade Seats Abstract ==========
    @Operation(summary = "15 - Duration Wise Trade Seats Abstract")
    @GetMapping("/trade-duration-seats")
    public List<TradeDurationSeatsResponse> getTradeDurationSeats(
            @RequestParam String year,
            @RequestParam String durationMonths,
            @RequestParam String itiType) {
        return reportService.getTradeDurationSeats(year, durationMonths, itiType);
    }

    // ========== 16 - Govt/Pvt District Wise Seats Abstract ==========
    @Operation(summary = "16 - Govt/Pvt District Wise Seats Abstract")
    @GetMapping("/govt-pvt-seats")
    public List<GovtPvtSeatsAbstractResponse> getGovtPvtSeatsAbstract(
            @RequestParam String year) {
        return reportService.getGovtPvtSeatsAbstract(year);
    }

    // ========== 17 - Student Reg Details ==========
    @Operation(summary = "17 - Student Reg Details")
    @GetMapping("/student-details")
    public StudentCompleteDetailsResponse getStudentCompleteDetails(
            @RequestParam(required = false) String regid,
            @RequestParam(required = false) String admNum) {
        return reportService.getStudentCompleteDetails(regid, admNum);
    }

    // ========== 18 - Verification Report (District) ==========
    @Operation(summary = "18 - Verification Report (District)")
    @GetMapping("/district-wise-application-count")
    public List<DistrictWiseApplicationCountResponse> getDistrictWiseApplicationCount(
            @RequestParam String year) {
        return reportService.getDistrictWiseApplicationCount(year);
    }

    // ========== 19 - District Schedule ==========
    @Operation(summary = "19 - District Schedule")
    @GetMapping("/district-schedule")
    public List<DistrictScheduleResponse> getDistrictSchedule(
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getDistrictSchedule(distCode, page, safeSize);
    }

    // ========== 20 - Shift Unit Report ==========
    @Operation(summary = "20 - Shift Unit Report")
    @GetMapping("/permitted-shift-unit")
    public List<ShiftUnitResponse> getPermittedShiftUnit(
            @RequestParam String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getPermittedShiftUnit(distCode, itiCode, page, safeSize);
    }

    // ========== 21 - Admitted Seats Abstract ==========
    @Operation(summary = "21 - Admitted Seats Abstract")
    @GetMapping("/iti-admissions")
    public List<ITIAdmissionsReportResponse> getITIAdmissionsReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String govt,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String gender,
            @RequestParam(required = false, defaultValue = "All") String ncvtScvt,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getITIAdmissionsReport(year, distCode, govt, caste, gender, ncvtScvt, page, safeSize);
    }

    // ========== 22 - All Resource Role ==========
    @Operation(summary = "22 - All Resource Role")
    @GetMapping("/all-resource-roles")
    public List<AllResourceRoleResponse> getAllResourceRoles(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getAllResourceRoles(page, safeSize);
    }

    // ========== 23 - DistWise Admitted Seats Abstract ==========
    @Operation(summary = "23 - DistWise Admitted Seats Abstract")
    @GetMapping("/strength-filled-seats")
    public List<StrengthFilledSeatsResponse> getStrengthFilledSeats(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getStrengthFilledSeatsAbstract(year, distCode);
    }

    // ========== 24 - Trade/Dist Wise Admission Report ==========
    @Operation(summary = "24 - Trade/Dist Wise Admission Report")
    @GetMapping("/admission-report")
    public List<AdmissionReportResponse> getAdmissionReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String pwd) {
        return reportService.getAdmissionReport(year, caste, pwd);
    }

    // ========== 25 - TradeWise Vacant Position ==========
    @Operation(summary = "25 - TradeWise Vacant Position")
    @GetMapping("/trade-vacant-positions")
    public List<TradeWiseVacantResponse> getTradeWiseVacantPositions(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getTradeWiseVacantPositions(year, distCode);
    }


    // ========== TRADE DISPLAY ==========
    @Operation(summary = "Trade Display - District Options")
    @GetMapping("/trade-display/districts")
    public List<DistrictOptionResponse> getDistrictOptions() {
        return tradeDisplayReportService.getDistrictOptions();
    }

}