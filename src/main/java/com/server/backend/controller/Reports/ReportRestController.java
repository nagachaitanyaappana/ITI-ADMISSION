package com.server.backend.controller.Reports;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.AdmissionReportResponse;
import com.server.backend.DTO.Reports.AllResourceRoleResponse;
import com.server.backend.DTO.Reports.ApiDashboardResponse;
import com.server.backend.DTO.Reports.ApplicantCountResponse;
import com.server.backend.DTO.Reports.ApplicantMobileAddressResponse;
import com.server.backend.DTO.Reports.ApplicantReportResponse;
import com.server.backend.DTO.Reports.CasteWiseAdmissionsResponse;
import com.server.backend.DTO.Reports.CollegeWiseOpenSeatsResponse;
import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.DTO.Reports.DistrictScheduleResponse;
import com.server.backend.DTO.Reports.DistrictWiseApplicationCountResponse;
import com.server.backend.DTO.Reports.DscFullReportResponse;
import com.server.backend.DTO.Reports.ITIAdmissionsReportResponse;
import com.server.backend.DTO.Reports.ItiTradeDisplayResponse;
import com.server.backend.DTO.Reports.ItiWiseStatusResponse;
import com.server.backend.DTO.Reports.MetadataResponse;
import com.server.backend.DTO.Reports.OpenSeatsAbstractResponse;
import com.server.backend.DTO.Reports.PhaseWiseReportResponse;
import com.server.backend.DTO.Reports.ShiftUnitResponse;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse;
import com.server.backend.DTO.Reports.StudentListResponse;
import com.server.backend.DTO.Reports.TodayScheduleResponse;
import com.server.backend.DTO.Reports.TradeDisplayReportRequest;
import com.server.backend.DTO.Reports.TradeDurationSeatsResponse;
import com.server.backend.DTO.Reports.TradeWiseReportResponse;
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

    // ========== LEGACY TRADE DISPLAY ==========

    @Operation(summary = "Get district options for dropdown", description = "Retrieves a list of districts to populate dropdown filters in the trade display report UI")
    @GetMapping("/trade-display/districts")
    public List<DistrictOptionResponse> getDistrictOptions() {
        return tradeDisplayReportService.getDistrictOptions();
    }

    @Operation(summary = "Trade Display Report", description = "Generates a trade-wise display report for ITIs filtered by district and ITI type (Government/Private)")
    @PostMapping("/trade-display")
    public List<ItiTradeDisplayResponse> getTradeDisplayReport(
            @RequestBody TradeDisplayReportRequest request) {
        return tradeDisplayReportService.getTradeDisplayReport(request);
    }

    // ========== METADATA ==========

    @Operation(summary = "Get metadata (years, phases, castes, levels)", description = "Fetches reference data including available years, admission phases, caste categories, and course levels for report filters")
    @GetMapping("/metadata")
    public MetadataResponse getMetadata() {
        return reportService.getMetadata();
    }

    // ========== 1. PHASE WISE ADMISSIONS DETAILS ==========

    @Operation(summary = "Phase Wise Admissions Details", description = "Provides an abstract summary of admissions categorized by admission phase for a selected academic year")
    @GetMapping("/phase-wise")
    public List<PhaseWiseReportResponse> getPhaseWiseReport(
            @RequestParam String year) {
        return reportService.getPhaseWiseReport(year);
    }

    // ========== 2. DIST/ITI/TRADE WISE SEATS ABSTRACT ==========

    @Operation(summary = "DIST/ITI/Trade Wise Seats Abstract", description = "Returns district-wise summary of open seats available across ITIs for a given academic year")
    @GetMapping("/open-seats")
    public List<OpenSeatsAbstractResponse> getOpenSeatsAbstract(
            @RequestParam String year) {
        return reportService.getOpenSeatsAbstract(year);
    }

    // ========== 3. DIST/ITI/TRADE WISE SEATS ABSTRACT (College) ==========

    @Operation(summary = "DIST/ITI/Trade Wise Seats Abstract - College wise", description = "Lists open seat details at college/ITI level for a specific district and academic year")
    @GetMapping("/open-seats/college")
    public List<CollegeWiseOpenSeatsResponse> getCollegeWiseOpenSeats(
            @RequestParam String year,
            @RequestParam String distCode) {
        return reportService.getCollegeWiseOpenSeats(year, distCode);
    }

    // ========== 4. DURATION WISE TRADE SEATS ABSTRACT ==========

    @Operation(summary = "Duration Wise Trade Seats Abstract", description = "Provides seat availability abstract grouped by trade duration (e.g., 6 months, 1 year, 2 years) for a selected year and ITI type")
    @GetMapping("/trade-duration-seats")
    public List<TradeDurationSeatsResponse> getTradeDurationSeats(
            @RequestParam String year,
            @RequestParam String durationMonths,
            @RequestParam String itiType) {
        return reportService.getTradeDurationSeats(year, durationMonths, itiType);
    }

    // ========== 5. TRADE/DIST WISE ADMISSION REPORT ==========

    @Operation(summary = "Trade/Dist Wise Admission Report", description = "Generates trade-wise admission report showing seat utilization broken down by gender and caste category")
    @GetMapping("/admission-report")
    public List<AdmissionReportResponse> getAdmissionReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String caste,
            @RequestParam(required = false, defaultValue = "All") String pwd) {
        return reportService.getAdmissionReport(year, caste, pwd);
    }

    // ========== 6. APPLICANT COUNT ==========

    @Operation(summary = "Applicant Count Nodal Report", description = "Returns the total count of applicants for a specific year and admission phase, used for nodal officer reporting")
    @GetMapping("/applicant-count")
    public ApplicantCountResponse getApplicantCount(
            @RequestParam String year,
            @RequestParam String phase) {
        return reportService.getApplicantCount(year, phase);
    }

    // ========== 7. ADMITTED SEATS ABSTRACT ==========

    @Operation(summary = "Admitted Seats Abstract", description = "Provides detailed admissions data for ITIs with optional filters for district, government/private status, caste, gender, and NCVT/SCVT affiliation")
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

    // ========== 8. DSC LIST ==========

    @Operation(summary = "DSC List", description = "Generates a comprehensive District Selection Committee (DSC) admission selection report for a specific ITI, trade, phase, and mode of admission")
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

    @Operation(summary = "API Dashboard (State/District)", description = "Displays district-wise verification status dashboard showing application verification metrics for a selected year")
    @GetMapping("/api-dashboard")
    public List<ApiDashboardResponse> getApiDashboard(
            @RequestParam String year) {
        return reportService.getApiDashboard(year);
    }

    // ========== 10. STUDENT COMPLETE DETAILS ==========

    @Operation(summary = "Student Registration Details", description = "Retrieves complete profile and application details for a student using either registration ID or admission number")
    @GetMapping("/student-details")
    public StudentCompleteDetailsResponse getStudentCompleteDetails(
            @RequestParam(required = false) String regid,
            @RequestParam(required = false) String admNum) {
        return reportService.getStudentCompleteDetails(regid, admNum);
    }

    // ========== 11. CASTE WISE ADMISSIONS ABSTRACT ==========

    @Operation(summary = "Caste Wise Admissions Abstract", description = "Provides admissions abstract grouped by caste category with optional filters for district, government/private status, phase, and gender")
    @GetMapping("/caste-wise-admissions")
    public List<CasteWiseAdmissionsResponse> getCasteWiseAdmissions(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String govt,
            @RequestParam(required = false, defaultValue = "All") String phase,
            @RequestParam(required = false, defaultValue = "All") String gender) {
        return reportService.getCasteWiseAdmissions(year, distCode, govt, phase, gender);
    }

    // ========== 12. VERIFICATION REPORT ==========

    @Operation(summary = "Verification Report", description = "Returns count of verified applications for a selected year and district, used for tracking verification progress")
    @GetMapping("/verified-application-count")
    public List<VerifiedApplicationCountResponse> getVerifiedApplicationCount(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getVerifiedApplicationCount(year, distCode);
    }

    // ========== 13. SHIFT UNIT REPORT ==========

    @Operation(summary = "Shift Unit Report", description = "Lists permitted shift and unit combinations for ITIs in a district, used for seat matrix and admission planning")
    @GetMapping("/permitted-shift-unit")
    public List<ShiftUnitResponse> getPermittedShiftUnit(
            @RequestParam String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiCode) {
        return reportService.getPermittedShiftUnit(distCode, itiCode);
    }

    // ========== 14. APPLICANT REPORT ==========

    @Operation(summary = "Applicant Report", description = "Generates applicant statistics report filtered by admission phase, with optional filters for year, ITI, and district")
    @GetMapping("/applicant-report-by-phase")
    public List<ApplicantReportResponse> getApplicantReportByPhase(
            @RequestParam String phase,
            @RequestParam(required = false) String year,
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getApplicantReportByPhase(phase, year, itiCode, distCode);
    }

    // ========== 15. ITI WISE ADMISSION STATUS ==========

    @Operation(summary = "API Dashboard (ITI)", description = "Provides admission status summary at ITI level showing seat filling status for a selected year with optional district and ITI filters")
    @GetMapping("/iti-wise-status")
    public List<ItiWiseStatusResponse> getItiWiseStatus(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiCode) {
        return reportService.getItiWiseStatus(year, distCode, itiCode);
    }

    // ========== 16. ITI STUDENT LIST ==========

    @Operation(summary = "ITI Student List Report", description = "Retrieves list of admitted students for a selected year with optional filters for ITI and district")
    @GetMapping("/iti-student-list")
    public List<StudentListResponse> getItiStudentList(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getItiStudentList(year, itiCode, distCode);
    }

    // ========== 17. TRADEWISE VACANT POSITION ==========

    @Operation(summary = "TradeWise Vacant Position", description = "Generates trade-wise admission summary report showing seat allocation across different trades for a selected year")
    @GetMapping("/trade-wise-report")
    public List<TradeWiseReportResponse> getTradeWiseReport(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @RequestParam(required = false, defaultValue = "All") String itiType) {
        return reportService.getTradeWiseReport(year, distCode, itiType);
    }

    // ========== 18. TODAY SCHEDULE ITIS ==========

    @Operation(summary = "Today Schedule ITIs", description = "Lists all ITIs that have scheduled admissions for today, showing their merit ranges and scheduled timings")
    @GetMapping("/today-schedule")
    public List<TodayScheduleResponse> getTodaySchedule() {
        return reportService.getTodaySchedule();
    }

    // ========== 19. DISTRICT SCHEDULE ==========

    @Operation(summary = "District Schedule", description = "Retrieves admission schedule details for all ITIs within a specific district")
    @GetMapping("/district-schedule")
    public List<DistrictScheduleResponse> getDistrictSchedule(
            @RequestParam String distCode) {
        return reportService.getDistrictSchedule(distCode);
    }

    // ========== 20. ALL RESOURCE ROLE ==========

    @Operation(summary = "All Resource Role", description = "Lists all registered users with their roles, assigned districts/ITIs, and contact information for user management")
    @GetMapping("/all-resource-roles")
    public List<AllResourceRoleResponse> getAllResourceRoles() {
        return reportService.getAllResourceRoles();
    }

    // ========== 21. APPLICANT ADDRESS WITH MOBILE ==========

    @Operation(summary = "Applicant Address With Mobile", description = "Retrieves applicant contact information including address and mobile numbers for a selected year and district")
    @GetMapping("/applicant-mobile-address")
    public List<ApplicantMobileAddressResponse> getApplicantMobileAddress(
            @RequestParam String year,
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getApplicantMobileAddress(year, distCode);
    }

    // ========== 22. DISTRICT WISE APPLICATION COUNT ==========

    @Operation(summary = "District Wise Application Count", description = "Returns district-wise count of applications with approved, rejected, and unverified statuses for a selected year")
    @GetMapping("/district-wise-application-count")
    public List<DistrictWiseApplicationCountResponse> getDistrictWiseApplicationCount(
            @RequestParam String year) {
        return reportService.getDistrictWiseApplicationCount(year);
    }
}