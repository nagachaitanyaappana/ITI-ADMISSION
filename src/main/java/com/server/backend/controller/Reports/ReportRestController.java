package com.server.backend.controller.Reports;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.AdmissionReportDetailResponse;
import com.server.backend.DTO.AdmissionReportResponse;
import com.server.backend.DTO.AllResourceRoleResponse;
import com.server.backend.DTO.ApiDashboardResponse;
import com.server.backend.DTO.ApiListResponse;
import com.server.backend.DTO.ApplicantMobileAddressResponse;
import com.server.backend.DTO.ApplicantCountDistrictResponse;
import com.server.backend.DTO.ApplicantReportResponse;
import com.server.backend.DTO.CasteWiseAdmissionsResponse;
import com.server.backend.DTO.DistrictOptionResponse;
import com.server.backend.DTO.DistrictScheduleResponse;
import com.server.backend.DTO.DistrictWiseApplicationCountResponse;
import com.server.backend.DTO.DscFullReportResponse;
import com.server.backend.DTO.GovtPvtSeatsAbstractResponse;
import com.server.backend.DTO.ITIAdmissionsReportResponse;
import com.server.backend.DTO.ItiWiseStatusResponse;
import com.server.backend.DTO.OpenSeatsAbstractResponse;
import com.server.backend.DTO.PhaseWiseReportResponse;
import com.server.backend.DTO.ShiftUnitResponse;
import com.server.backend.DTO.StateDashboardResponse;
import com.server.backend.DTO.StrengthFilledSeatsResponse;
import com.server.backend.DTO.StudentCompleteDetailsResponse;
import com.server.backend.DTO.TodayScheduleResponse;
import com.server.backend.DTO.TradeDurationSeatsResponse;
import com.server.backend.DTO.TradeWiseReportResponse;
import com.server.backend.DTO.TradeWiseVacantResponse;
import com.server.backend.DTO.VerifiedApplicationCountReportResponse;
import com.server.backend.DTO.ItiListResponse;
import com.server.backend.DTO.NotAdmittedStudentResponse;
import com.server.backend.DTO.ItiTradeDisplayResponse;
import com.server.backend.DTO.DscOptionsResponse;
import com.server.backend.DTO.CurrentAdmissionPhaseResponse;
import com.server.backend.DTO.TradeDisplayReportRequest;
import com.server.backend.service.Reports.ReportService;
import com.server.backend.service.Reports.TradeDisplayReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reports")
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
    public ApiListResponse<ItiWiseStatusResponse> getItiWiseStatus(
            @RequestParam(required = false) String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 10000);
        return new ApiListResponse<>(reportService.getItiWiseStatus(year, distCode, itiCode, page, safeSize));
    }

    // ========== 2 - Applicant Report ==========
    @Operation(summary = "2 - Applicant Report")
    @GetMapping("/applicant-report-by-phase")
    public ApiListResponse<ApplicantReportResponse> getApplicantReportByPhase(
            @RequestParam String phase,
            @RequestParam(required = false) String year,
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 10000);
        return new ApiListResponse<>(reportService.getApplicantReportByPhase(phase, year, itiCode, distCode, page, safeSize));
    }

    // ========== 2a - Current Admission Phase ==========
    @Operation(summary = "2a - Current Admission Phase")
    @GetMapping("/current-admission-phase")
    public CurrentAdmissionPhaseResponse getCurrentAdmissionPhase() {
        return reportService.getCurrentAdmissionPhase();
    }

    // ========== 3 - Admission Report (ITI) ==========
    @Operation(summary = "3 - Admission Report (ITI)")
    @GetMapping("/admission-report-iti")
    public ApiListResponse<AdmissionReportDetailResponse> getAdmissionReportIti(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "500") int size) {
        int safeSize = Math.min(size, 10000);
        return new ApiListResponse<>(reportService.getAdmissionReportDetails(page, safeSize));
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

    // ========== 4a - DSC Options ==========
    @Operation(summary = "4a - DSC Options")
    @GetMapping("/dsc-options")
    public DscOptionsResponse getDscOptions(
            @RequestParam(required = false, name = "dist_code") String distCode,
            @RequestParam(required = false, name = "iti_code") String itiCode) {
        return reportService.getDscOptions(distCode, itiCode);
    }

    // ========== 5 - Caste Wise Admissions Abstract ==========
    @Operation(summary = "5 - Caste Wise Admissions Abstract")
    @GetMapping("/caste-wise-admissions")
    public ApiListResponse<CasteWiseAdmissionsResponse> getCasteWiseAdmissions(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String govt,
            @RequestParam(required = false, defaultValue = "All") String phase,
            @RequestParam(required = false, defaultValue = "All") String gender) {
        return new ApiListResponse<>(reportService.getCasteWiseAdmissions(year, distCode, govt, phase, gender));
    }

    @GetMapping("/applicant-count-district-wise")
    public ApiListResponse<ApplicantCountDistrictResponse> getApplicantCountDistrictWise(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String govt,
            @RequestParam(required = false, defaultValue = "All") String phase) {
        return new ApiListResponse<>(reportService.getApplicantCountDistrictWise(year, distCode, govt, phase));
    }

    // ========== 6 - Applicant Address With Mobile ==========
    @Operation(summary = "6 - Applicant Address With Mobile")
    @GetMapping("/applicant-mobile-address")
    public ApiListResponse<ApplicantMobileAddressResponse> getApplicantMobileAddress(
            @RequestParam(required = false) String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 10000);
        return new ApiListResponse<>(reportService.getApplicantMobileAddress(year, distCode, page, safeSize));
    }

    // ========== 7 - API Dashboard (District) ==========
    @Operation(summary = "7 - API Dashboard (District)")
    @GetMapping("/api-dashboard")
    public ApiListResponse<ApiDashboardResponse> getApiDashboard(
            @RequestParam(required = false) String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return new ApiListResponse<>(reportService.getApiDashboard(year, distCode));
    }

    // ========== 8 - Verification Report (District) ==========
    @Operation(summary = "8 - Verification Report (District)")
    @GetMapping("/verified-application-count")
    public VerifiedApplicationCountReportResponse getVerifiedApplicationCount(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getVerifiedApplicationCount(year, distCode);
    }

    // ========== 9 - State Dashboard ==========
    @Operation(summary = "9 - State Dashboard")
    @GetMapping("/state-dashboard")
    public ApiListResponse<StateDashboardResponse> getStateDashboard(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String govt) {
        return new ApiListResponse<>(reportService.getStateDashboard(year, govt));
    }

    // ========== 10 - Phase Wise Admissions Details ==========
    @Operation(summary = "10 - Phase Wise Admissions Details")
    @GetMapping("/phase-wise")
    public ApiListResponse<PhaseWiseReportResponse> getPhaseWiseReport(
            @RequestParam String year) {
        return new ApiListResponse<>(reportService.getPhaseWiseReport(year));
    }

    // ========== 11 - Today Schedule ITIs ==========
    @Operation(summary = "11 - Today Schedule ITIs")
    @GetMapping("/today-schedule")
    public ApiListResponse<TodayScheduleResponse> getTodaySchedule() {
        return new ApiListResponse<>(reportService.getTodaySchedule());
    }

    // ========== 12 - Trade Wise Report ==========
    @Operation(summary = "12 - Trade Wise Report")
    @GetMapping("/trade-wise-report")
    public ApiListResponse<TradeWiseReportResponse> getTradeWiseReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiType) {
        return new ApiListResponse<>(reportService.getTradeWiseReport(year, distCode, itiType));
    }

    // ========== 13 - Applicant Report Dist Wise ==========
    @Operation(summary = "13 - Applicant Report Dist Wise")
    @GetMapping("/applicant-report-state-wise")
    public ApiListResponse<ApplicantCountDistrictResponse> getApplicantReportStateWise(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String govt,
            @RequestParam(required = false, defaultValue = "All") String phase) {
        return new ApiListResponse<>(reportService.getApplicantCountDistrictWise(year, distCode, govt, phase));
    }

    // ========== 14 - DIST/ITI/Trade Wise Seats Abstract ==========
    @Operation(summary = "14 - DIST/ITI/Trade Wise Seats Abstract")
    @GetMapping("/open-seats")
    public ApiListResponse<OpenSeatsAbstractResponse> getOpenSeatsAbstract(
            @RequestParam String year) {
        return new ApiListResponse<>(reportService.getOpenSeatsAbstract(year));
    }

    // ========== 15 - Duration Wise Trade Seats Abstract ==========
    @Operation(summary = "15 - Duration Wise Trade Seats Abstract")
    @GetMapping("/trade-duration-seats")
    public ApiListResponse<TradeDurationSeatsResponse> getTradeDurationSeats(
            @RequestParam String year,
            @RequestParam String durationMonths,
            @RequestParam String itiType) {
        return new ApiListResponse<>(reportService.getTradeDurationSeats(year, durationMonths, itiType));
    }

    // ========== 16 - Govt/Pvt District Wise Seats Abstract ==========
    @Operation(summary = "16 - Govt/Pvt District Wise Seats Abstract")
    @GetMapping("/govt-pvt-seats")
    public ApiListResponse<GovtPvtSeatsAbstractResponse> getGovtPvtSeatsAbstract(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String govt) {
        return new ApiListResponse<>(reportService.getGovtPvtSeatsAbstract(year, govt));
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
    public ApiListResponse<DistrictWiseApplicationCountResponse> getDistrictWiseApplicationCount(
            @RequestParam String year) {
        return new ApiListResponse<>(reportService.getDistrictWiseApplicationCount(year));
    }

    // ========== 19 - District Schedule ==========
    @Operation(summary = "19 - District Schedule")
    @GetMapping("/district-schedule")
    public ApiListResponse<DistrictScheduleResponse> getDistrictSchedule(
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "2025") String year,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 10000);
        return new ApiListResponse<>(reportService.getDistrictSchedule(distCode, year, page, safeSize));
    }

    // ========== 20 - Shift Unit Report ==========
    @Operation(summary = "20 - Shift Unit Report")
    @GetMapping("/permitted-shift-unit")
    public ApiListResponse<ShiftUnitResponse> getPermittedShiftUnit(
            @RequestParam String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 10000);
        return new ApiListResponse<>(reportService.getPermittedShiftUnit(distCode, itiCode, page, safeSize));
    }

    // ========== 21 - Admitted Seats Abstract ==========
    @Operation(summary = "21 - Admitted Seats Abstract")
    @GetMapping("/iti-admissions")
    public ApiListResponse<ITIAdmissionsReportResponse> getITIAdmissionsReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String govt,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String gender,
            @RequestParam(required = false, defaultValue = "All") String ncvtScvt,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 10000);
        return new ApiListResponse<>(reportService.getITIAdmissionsReport(year, distCode, govt, caste, gender, ncvtScvt, page, safeSize));
    }

    // ========== 22 - All Resource Role ==========
    @Operation(summary = "22 - All Resource Role")
    @GetMapping("/all-resource-roles")
    public ApiListResponse<AllResourceRoleResponse> getAllResourceRoles(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        int safeSize = Math.min(size, 10000);
        return new ApiListResponse<>(reportService.getAllResourceRoles(page, safeSize));
    }

    // ========== 23 - DistWise Admitted Seats Abstract ==========
    @Operation(summary = "23 - DistWise Admitted Seats Abstract")
    @GetMapping("/strength-filled-seats")
    public ApiListResponse<StrengthFilledSeatsResponse> getStrengthFilledSeats(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return new ApiListResponse<>(reportService.getStrengthFilledSeatsAbstract(year, distCode));
    }

    // ========== 24 - Trade/Dist Wise Admission Report ==========
    @Operation(summary = "24 - Trade/Dist Wise Admission Report")
    @GetMapping("/admission-report")
    public ApiListResponse<AdmissionReportResponse> getAdmissionReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String pwd) {
        return new ApiListResponse<>(reportService.getAdmissionReport(year, caste, pwd));
    }

    // ========== 25 - TradeWise Vacant Position ==========
    @Operation(summary = "25 - TradeWise Vacant Position")
    @GetMapping("/trade-vacant-positions")
    public ApiListResponse<TradeWiseVacantResponse> getTradeWiseVacantPositions(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return new ApiListResponse<>(reportService.getTradeWiseVacantPositions(year, distCode));
    }


    // ========== TRADE DISPLAY ==========
    @Operation(summary = "Trade Display - District Options")
    @GetMapping("/trade-display/districts")
    public ApiListResponse<DistrictOptionResponse> getDistrictOptions() {
        return new ApiListResponse<>(tradeDisplayReportService.getDistrictOptions());
    }

    @Operation(summary = "Trade Display - ITI List with Trades & Strengths")
    @GetMapping("/trade-display/itis")
    public ApiListResponse<ItiTradeDisplayResponse> getTradeDisplayReport(
            @RequestParam String dist,
            @RequestParam(required = false) String type) {
        TradeDisplayReportRequest request = new TradeDisplayReportRequest();
        request.setDist(dist);
        request.setType(type);
        return new ApiListResponse<>(tradeDisplayReportService.getTradeDisplayReport(request));
    }

    @Operation(summary = "ITI List - All ITIs filtered by type")
    @GetMapping("/trade-display/iti-list")
    public ApiListResponse<ItiListResponse> getItiList(
            @RequestParam(required = false) String type) {
        return new ApiListResponse<>(tradeDisplayReportService.getItiList(type));
    }

    // ========== 27 - Students Not Admitted ==========
    @Operation(summary = "27 - Students Not Admitted - available years (base + partition tables)")
    @GetMapping("/students-not-admitted/years")
    public ApiListResponse<String> getStudentsNotAdmittedYears() {
        return new ApiListResponse<>(reportService.getStudentsNotAdmittedYears());
    }

    @Operation(summary = "27 - Students Not Admitted (registered but no admission record)")
    @GetMapping("/students-not-admitted")
    public ApiListResponse<NotAdmittedStudentResponse> getStudentsNotAdmitted(
            @RequestParam String year,
            @RequestParam(required = false) Integer phase,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "500") int size) {
        int safeSize = Math.min(size, 5000);
        List<NotAdmittedStudentResponse> rows =
                reportService.getStudentsNotAdmitted(year, phase, page, safeSize);
        ApiListResponse<NotAdmittedStudentResponse> response = new ApiListResponse<>(rows);
        response.setCount((int) reportService.countStudentsNotAdmitted(year, phase));
        return response;
    }
}