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
    @Operation(summary = "1 - API Dashboard (ITI)", operationId = "01-iti-wise-status")
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
    @Operation(summary = "2 - Applicant Report", operationId = "02-applicant-report-by-phase")
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
    @Operation(summary = "3 - Admission Report (ITI)", operationId = "03-admission-report-iti")
    @GetMapping("/admission-report-iti")
    public List<AdmissionReportResponse> getAdmissionReportIti(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String pwd) {
        return reportService.getAdmissionReport(year, caste, pwd);
    }

    // ========== 4 - DSC List ==========
    @Operation(summary = "4 - DSC List", operationId = "04-dsc-full")
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
    @Operation(summary = "5 - Caste Wise Admissions Abstract", operationId = "05-caste-wise-admissions")
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
    @Operation(summary = "6 - Applicant Address With Mobile", operationId = "06-applicant-mobile-address")
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
    @Operation(summary = "7 - API Dashboard (District)", operationId = "07-api-dashboard")
    @GetMapping("/api-dashboard")
    public List<ApiDashboardResponse> getApiDashboard(
            @RequestParam String year) {
        return reportService.getApiDashboard(year);
    }

    // ========== 8 - Verification Report (District) ==========
    @Operation(summary = "8 - Verification Report (District)", operationId = "08-verified-application-count")
    @GetMapping("/verified-application-count")
    public List<VerifiedApplicationCountResponse> getVerifiedApplicationCount(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getVerifiedApplicationCount(year, distCode);
    }

    // ========== 9 - State Dashboard ==========
    @Operation(summary = "9 - State Dashboard", operationId = "09-state-dashboard")
    @GetMapping("/state-dashboard")
    public List<StateDashboardResponse> getStateDashboard(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String govt) {
        return reportService.getStateDashboard(year, govt);
    }

    // ========== 10 - Phase Wise Admissions Details ==========
    @Operation(summary = "10 - Phase Wise Admissions Details", operationId = "10-phase-wise")
    @GetMapping("/phase-wise")
    public List<PhaseWiseReportResponse> getPhaseWiseReport(
            @RequestParam String year) {
        return reportService.getPhaseWiseReport(year);
    }

    // ========== 11 - Today Schedule ITIs ==========
    @Operation(summary = "11 - Today Schedule ITIs", operationId = "11-today-schedule")
    @GetMapping("/today-schedule")
    public List<TodayScheduleResponse> getTodaySchedule() {
        return reportService.getTodaySchedule();
    }

    // ========== 12 - Trade Wise Report ==========
    @Operation(summary = "12 - Trade Wise Report", operationId = "12-trade-wise-report")
    @GetMapping("/trade-wise-report")
    public List<TradeWiseReportResponse> getTradeWiseReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiType) {
        return reportService.getTradeWiseReport(year, distCode, itiType);
    }

    // ========== 13 - Applicant Report Dist Wise ==========
    @Operation(summary = "13 - Applicant Report Dist Wise", operationId = "13-applicant-report-state-wise")
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
    @Operation(summary = "14 - DIST/ITI/Trade Wise Seats Abstract", operationId = "14-open-seats")
    @GetMapping("/open-seats")
    public List<OpenSeatsAbstractResponse> getOpenSeatsAbstract(
            @RequestParam String year) {
        return reportService.getOpenSeatsAbstract(year);
    }

    // ========== 15 - Duration Wise Trade Seats Abstract ==========
    @Operation(summary = "15 - Duration Wise Trade Seats Abstract", operationId = "15-trade-duration-seats")
    @GetMapping("/trade-duration-seats")
    public List<TradeDurationSeatsResponse> getTradeDurationSeats(
            @RequestParam String year,
            @RequestParam String durationMonths,
            @RequestParam String itiType) {
        return reportService.getTradeDurationSeats(year, durationMonths, itiType);
    }

    // ========== 16 - Govt/Pvt District Wise Seats Abstract ==========
    @Operation(summary = "16 - Govt/Pvt District Wise Seats Abstract", operationId = "16-govt-pvt-seats")
    @GetMapping("/govt-pvt-seats")
    public List<GovtPvtSeatsAbstractResponse> getGovtPvtSeatsAbstract(
            @RequestParam String year) {
        return reportService.getGovtPvtSeatsAbstract(year);
    }

    // ========== 17 - Student Reg Details ==========
    @Operation(summary = "17 - Student Reg Details", operationId = "17-student-details")
    @GetMapping("/student-details")
    public StudentCompleteDetailsResponse getStudentCompleteDetails(
            @RequestParam(required = false) String regid,
            @RequestParam(required = false) String admNum) {
        return reportService.getStudentCompleteDetails(regid, admNum);
    }

    // ========== 18 - Verification Report (District) ==========
    @Operation(summary = "18 - Verification Report (District)", operationId = "18-district-wise-application-count")
    @GetMapping("/district-wise-application-count")
    public List<DistrictWiseApplicationCountResponse> getDistrictWiseApplicationCount(
            @RequestParam String year) {
        return reportService.getDistrictWiseApplicationCount(year);
    }

    // ========== 19 - District Schedule ==========
    @Operation(summary = "19 - District Schedule", operationId = "19-district-schedule")
    @GetMapping("/district-schedule")
    public List<DistrictScheduleResponse> getDistrictSchedule(
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getDistrictSchedule(distCode, page, safeSize);
    }

    // ========== 20 - Shift Unit Report ==========
    @Operation(summary = "20 - Shift Unit Report", operationId = "20-permitted-shift-unit")
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
    @Operation(summary = "21 - Admitted Seats Abstract", operationId = "21-iti-admissions")
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
    @Operation(summary = "22 - All Resource Role", operationId = "22-all-resource-roles")
    @GetMapping("/all-resource-roles")
    public List<AllResourceRoleResponse> getAllResourceRoles(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 500);
        return reportService.getAllResourceRoles(page, safeSize);
    }

    // ========== 23 - DistWise Admitted Seats Abstract ==========
    @Operation(summary = "23 - DistWise Admitted Seats Abstract", operationId = "23-strength-filled-seats")
    @GetMapping("/strength-filled-seats")
    public List<StrengthFilledSeatsResponse> getStrengthFilledSeats(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getStrengthFilledSeatsAbstract(year, distCode);
    }

    // ========== 24 - Trade/Dist Wise Admission Report ==========
    @Operation(summary = "24 - Trade/Dist Wise Admission Report", operationId = "24-admission-report")
    @GetMapping("/admission-report")
    public List<AdmissionReportResponse> getAdmissionReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String pwd) {
        return reportService.getAdmissionReport(year, caste, pwd);
    }

    // ========== 25 - TradeWise Vacant Position ==========
    @Operation(summary = "25 - TradeWise Vacant Position", operationId = "25-trade-vacant-positions")
    @GetMapping("/trade-vacant-positions")
    public List<TradeWiseVacantResponse> getTradeWiseVacantPositions(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getTradeWiseVacantPositions(year, distCode);
    }


    // ========== TRADE DISPLAY ==========
    @Operation(summary = "Trade Display - District Options", operationId = "26-trade-display-districts")
    @GetMapping("/trade-display/districts")
    public List<DistrictOptionResponse> getDistrictOptions() {
        return tradeDisplayReportService.getDistrictOptions();
    }

}