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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
        summary = "Get district options for dropdown",
        description = "Retrieves a list of all districts to populate dropdown filters in the trade display report UI. Returns district codes and names.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved district list", 
                        content = @Content(schema = @Schema(implementation = DistrictOptionResponse.class)))
        }
    )
    @GetMapping("/trade-display/districts")
    public List<DistrictOptionResponse> getDistrictOptions() {
        return tradeDisplayReportService.getDistrictOptions();
    }

    @Operation(
        summary = "Trade Display Report",
        description = "Generates a comprehensive trade-wise display report for ITIs filtered by district and ITI type (Government/Private). Shows trade details, seat matrix, and ITI information.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully generated trade display report",
                        content = @Content(schema = @Schema(implementation = ItiTradeDisplayResponse.class)))
        }
    )
    @PostMapping("/trade-display")
    public List<ItiTradeDisplayResponse> getTradeDisplayReport(
            @RequestBody TradeDisplayReportRequest request) {
        return tradeDisplayReportService.getTradeDisplayReport(request);
    }

    // ========== METADATA ==========

    @Operation(
        summary = "Get metadata (years, phases, castes, levels)",
        description = "Fetches reference data including available academic years, admission phases, caste categories, and course levels (CONVENER/ITI) for populating report filter dropdowns",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved metadata",
                        content = @Content(schema = @Schema(implementation = MetadataResponse.class)))
        }
    )
    @GetMapping("/metadata")
    public MetadataResponse getMetadata() {
        return reportService.getMetadata();
    }

    // ========== 1. PHASE WISE ADMISSIONS DETAILS ==========

    @Operation(
        summary = "Phase Wise Admissions Details",
        description = "Provides an abstract summary of admissions categorized by admission phase (Phase I, II, III, IV, V) for a selected academic year. Shows district-wise breakdown with phase-wise and total admission counts.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved phase-wise report",
                        content = @Content(schema = @Schema(implementation = PhaseWiseReportResponse.class)))
        }
    )
    @GetMapping("/phase-wise")
    public List<PhaseWiseReportResponse> getPhaseWiseReport(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year) {
        return reportService.getPhaseWiseReport(year);
    }

    // ========== 2. DIST/ITI/TRADE WISE SEATS ABSTRACT ==========

    @Operation(
        summary = "DIST/ITI/Trade Wise Seats Abstract",
        description = "Returns district-wise summary of open seats available across all ITIs for a given academic year. Shows total seats, filled seats, and vacant seats per district.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved open seats abstract",
                        content = @Content(schema = @Schema(implementation = OpenSeatsAbstractResponse.class)))
        }
    )
    @GetMapping("/open-seats")
    public List<OpenSeatsAbstractResponse> getOpenSeatsAbstract(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year) {
        return reportService.getOpenSeatsAbstract(year);
    }

    // ========== 3. DIST/ITI/TRADE WISE SEATS ABSTRACT (College) ==========

    @Operation(
        summary = "DIST/ITI/Trade Wise Seats Abstract - College wise",
        description = "Lists open seat details at college/ITI level for a specific district and academic year. Shows individual ITI-wise seat availability with total seats, filled seats, and vacant seats.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved college-wise open seats",
                        content = @Content(schema = @Schema(implementation = CollegeWiseOpenSeatsResponse.class)))
        }
    )
    @GetMapping("/open-seats/college")
    public List<CollegeWiseOpenSeatsResponse> getCollegeWiseOpenSeats(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "District code (e.g., 01, 02, etc.)", required = true, example = "01")
            @RequestParam String distCode) {
        return reportService.getCollegeWiseOpenSeats(year, distCode);
    }

    // ========== 4. DURATION WISE TRADE SEATS ABSTRACT ==========

    @Operation(
        summary = "Duration Wise Trade Seats Abstract",
        description = "Provides seat availability abstract grouped by trade duration (e.g., 6 months, 1 year, 2 years) for a selected year and ITI type (Government/Private). Shows total seats, filled seats, vacant seats, and fill percentage.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trade duration seats",
                        content = @Content(schema = @Schema(implementation = TradeDurationSeatsResponse.class)))
        }
    )
    @GetMapping("/trade-duration-seats")
    public List<TradeDurationSeatsResponse> getTradeDurationSeats(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "Duration in months (e.g., 6, 12, 24)", required = true, example = "12")
            @RequestParam String durationMonths,
            @Parameter(description = "ITI type: G for Government, P for Private", required = true, example = "G")
            @RequestParam String itiType) {
        return reportService.getTradeDurationSeats(year, durationMonths, itiType);
    }

    // ========== 5. TRADE/DIST WISE ADMISSION REPORT ==========

    @Operation(
        summary = "Trade/Dist Wise Admission Report",
        description = "Generates trade-wise admission report showing seat utilization broken down by gender (boys/girls) and caste category. Provides total admissions count per trade.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully generated admission report",
                        content = @Content(schema = @Schema(implementation = AdmissionReportResponse.class)))
        }
    )
    @GetMapping("/admission-report")
    public List<AdmissionReportResponse> getAdmissionReport(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "Caste category filter (e.g., OC, BC-A, BC-B, SC, ST, All)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String caste,
            @Parameter(description = "Physically disabled filter: Yes or No", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String pwd) {
        return reportService.getAdmissionReport(year, caste, pwd);
    }

    // ========== 6. APPLICANT COUNT ==========

    @Operation(
        summary = "Applicant Count Nodal Report",
        description = "Returns the total count of applicants for a specific year and admission phase, broken down by Government/Private ITIs. Used for nodal officer reporting and monitoring.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved applicant count",
                        content = @Content(schema = @Schema(implementation = ApplicantCountResponse.class)))
        }
    )
    @GetMapping("/applicant-count")
    public ApplicantCountResponse getApplicantCount(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "Admission phase (1, 2, 3, 4, or 5)", required = true, example = "1")
            @RequestParam String phase) {
        return reportService.getApplicantCount(year, phase);
    }

    // ========== 7. ADMITTED SEATS ABSTRACT ==========

    @Operation(
        summary = "Admitted Seats Abstract",
        description = "Provides detailed admissions data for ITIs with optional filters for district, government/private status, caste, gender, and NCVT/SCVT affiliation. Returns admission numbers, student names, and SSC registration numbers.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved admissions data",
                        content = @Content(schema = @Schema(implementation = ITIAdmissionsReportResponse.class)))
        }
    )
    @GetMapping("/iti-admissions")
    public List<ITIAdmissionsReportResponse> getITIAdmissionsReport(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "District code filter (e.g., 01, 02, or All for all districts)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @Parameter(description = "ITI type: G for Government, P for Private, All for both", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String govt,
            @Parameter(description = "Caste category filter (e.g., OC, BC-A, SC, ST, All)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String caste,
            @Parameter(description = "Gender filter: M for Male, F for Female, All for both", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String gender,
            @Parameter(description = "Course type: N for NCVT, S for SCVT, All for both", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String ncvtScvt,
            @Parameter(description = "Limit number of rows (e.g., 100, 500, or All for no limit)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String limitRows) {
        return reportService.getITIAdmissionsReport(year, distCode, govt, caste, gender, ncvtScvt, limitRows);
    }

    // ========== 8. DSC LIST ==========

    @Operation(
        summary = "DSC List",
        description = "Generates a comprehensive District Selection Committee (DSC) admission selection report for a specific ITI, trade, phase, and mode of admission. Shows candidate details with ranks, categories, and seat allocation.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully generated DSC report",
                        content = @Content(schema = @Schema(implementation = DscFullReportResponse.class)))
        }
    )
    @GetMapping("/dsc-full")
    public DscFullReportResponse getDscFullReport(
            @Parameter(description = "District code", required = true, example = "01")
            @RequestParam String distCode,
            @Parameter(description = "ITI code", required = true, example = "ITI001")
            @RequestParam String itiCode,
            @Parameter(description = "Trade code", required = true, example = "101")
            @RequestParam String tradeCode,
            @Parameter(description = "Admission phase (1, 2, 3, 4, or 5)", required = true, example = "1")
            @RequestParam String phase,
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "Mode of admission (e.g., AUGUST, FEBRUARY)", required = true, example = "AUGUST")
            @RequestParam String modeAdm) {
        return reportService.getDscFullReport(distCode, itiCode, tradeCode, phase, year, modeAdm);
    }

    // ========== 9. API DASHBOARD ==========

    @Operation(
        summary = "API Dashboard (State/District)",
        description = "Displays district-wise verification status dashboard showing application verification metrics for a selected year. Includes total applications, verified, pending, and duplicate records.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved dashboard data",
                        content = @Content(schema = @Schema(implementation = ApiDashboardResponse.class)))
        }
    )
    @GetMapping("/api-dashboard")
    public List<ApiDashboardResponse> getApiDashboard(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year) {
        return reportService.getApiDashboard(year);
    }

    // ========== 10. STUDENT COMPLETE DETAILS ==========

    @Operation(
        summary = "Student Registration Details",
        description = "Retrieves complete profile and application details for a student using either registration ID or admission number. Includes registration details, SSC marks, applied ITIs, merit list, and admission details.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student details",
                        content = @Content(schema = @Schema(implementation = StudentCompleteDetailsResponse.class)))
        }
    )
    @GetMapping("/student-details")
    public StudentCompleteDetailsResponse getStudentCompleteDetails(
            @Parameter(description = "Registration ID (optional if admNum is provided)", required = false, example = "12345")
            @RequestParam(required = false) String regid,
            @Parameter(description = "Admission number (optional if regid is provided)", required = false, example = "ADM2025001")
            @RequestParam(required = false) String admNum) {
        return reportService.getStudentCompleteDetails(regid, admNum);
    }

    // ========== 11. CASTE WISE ADMISSIONS ABSTRACT ==========

    @Operation(
        summary = "Caste Wise Admissions Abstract",
        description = "Provides admissions abstract grouped by caste category (BC-A, BC-B, SC, ST, OC, EWS, etc.) with optional filters for district, government/private status, phase, and gender. Shows category-wise admission counts.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved caste-wise admissions",
                        content = @Content(schema = @Schema(implementation = CasteWiseAdmissionsResponse.class)))
        }
    )
    @GetMapping("/caste-wise-admissions")
    public List<CasteWiseAdmissionsResponse> getCasteWiseAdmissions(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "District code filter (e.g., 01, 02, or All for all districts)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @Parameter(description = "ITI type: G for Government, P for Private, All for both", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String govt,
            @Parameter(description = "Admission phase (1, 2, 3, 4, 5, or All)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String phase,
            @Parameter(description = "Gender filter: M for Male, F for Female, All for both", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String gender) {
        return reportService.getCasteWiseAdmissions(year, distCode, govt, phase, gender);
    }

    // ========== 12. VERIFICATION REPORT ==========

    @Operation(
        summary = "Verification Report",
        description = "Returns count of verified applications for a selected year and district. Shows total applications, approved, rejected, and unverified counts for tracking verification progress.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved verification report",
                        content = @Content(schema = @Schema(implementation = VerifiedApplicationCountResponse.class)))
        }
    )
    @GetMapping("/verified-application-count")
    public List<VerifiedApplicationCountResponse> getVerifiedApplicationCount(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "District code filter (e.g., 01, 02, or All for all districts)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getVerifiedApplicationCount(year, distCode);
    }

    // ========== 13. SHIFT UNIT REPORT ==========

    @Operation(
        summary = "Shift Unit Report",
        description = "Lists permitted shift and unit combinations for ITIs in a district. Used for seat matrix configuration and admission planning. Shows trade-wise shift and unit allocations.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved shift unit data",
                        content = @Content(schema = @Schema(implementation = ShiftUnitResponse.class)))
        }
    )
    @GetMapping("/permitted-shift-unit")
    public List<ShiftUnitResponse> getPermittedShiftUnit(
            @Parameter(description = "District code", required = true, example = "01")
            @RequestParam String distCode,
            @Parameter(description = "ITI code (optional, use All for all ITIs in district)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String itiCode) {
        return reportService.getPermittedShiftUnit(distCode, itiCode);
    }

    // ========== 14. APPLICANT REPORT ==========

    @Operation(
        summary = "Applicant Report",
        description = "Generates applicant statistics report filtered by admission phase, with optional filters for year, ITI, and district. Shows applicant details including SSC registration number, mobile, and personal information.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully generated applicant report",
                        content = @Content(schema = @Schema(implementation = ApplicantReportResponse.class)))
        }
    )
    @GetMapping("/applicant-report-by-phase")
    public List<ApplicantReportResponse> getApplicantReportByPhase(
            @Parameter(description = "Admission phase (e.g., 1, 2, 3, 4, 5)", required = true, example = "1")
            @RequestParam String phase,
            @Parameter(description = "Academic year (optional)", required = false, example = "2025")
            @RequestParam(required = false) String year,
            @Parameter(description = "ITI code (optional, use All for all ITIs)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @Parameter(description = "District code (optional, use All for all districts)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getApplicantReportByPhase(phase, year, itiCode, distCode);
    }

    // ========== 15. ITI WISE ADMISSION STATUS ==========

    @Operation(
        summary = "API Dashboard (ITI)",
        description = "Provides admission status summary at ITI level showing seat filling status for a selected year. Includes total admissions, verified, pending, and duplicate records with optional district and ITI filters.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ITI-wise status",
                        content = @Content(schema = @Schema(implementation = ItiWiseStatusResponse.class)))
        }
    )
    @GetMapping("/iti-wise-status")
    public List<ItiWiseStatusResponse> getItiWiseStatus(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "District code filter (e.g., 01, 02, or All for all districts)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @Parameter(description = "ITI code (optional, use All for all ITIs)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String itiCode) {
        return reportService.getItiWiseStatus(year, distCode, itiCode);
    }

    // ========== 16. ITI STUDENT LIST ==========

    @Operation(
        summary = "ITI Student List Report",
        description = "Retrieves comprehensive list of admitted students for a selected year with optional filters for ITI and district. Includes student personal details, contact information, and admission details.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student list",
                        content = @Content(schema = @Schema(implementation = StudentListResponse.class)))
        }
    )
    @GetMapping("/iti-student-list")
    public List<StudentListResponse> getItiStudentList(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "ITI code (optional, use All for all ITIs)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String itiCode,
            @Parameter(description = "District code (optional, use All for all districts)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getItiStudentList(year, itiCode, distCode);
    }

    // ========== 17. TRADEWISE VACANT POSITION ==========

    @Operation(
        summary = "TradeWise Vacant Position",
        description = "Generates trade-wise admission summary report showing seat allocation across different trades for a selected year. Displays total strength, filled seats, and vacant positions per trade.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully generated trade-wise report",
                        content = @Content(schema = @Schema(implementation = TradeWiseReportResponse.class)))
        }
    )
    @GetMapping("/trade-wise-report")
    public List<TradeWiseReportResponse> getTradeWiseReport(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "District code filter (e.g., 01, 02, or All for all districts)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String distCode,
            @Parameter(description = "ITI type: G for Government, P for Private, All for both", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String itiType) {
        return reportService.getTradeWiseReport(year, distCode, itiType);
    }

    // ========== 18. TODAY SCHEDULE ITIS ==========

    @Operation(
        summary = "Today Schedule ITIs",
        description = "Lists all ITIs that have scheduled admissions for today, showing their merit ranges, scheduled timings, and district information. Useful for tracking daily admission schedules.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved today's schedule",
                        content = @Content(schema = @Schema(implementation = TodayScheduleResponse.class)))
        }
    )
    @GetMapping("/today-schedule")
    public List<TodayScheduleResponse> getTodaySchedule() {
        return reportService.getTodaySchedule();
    }

    // ========== 19. DISTRICT SCHEDULE ==========

    @Operation(
        summary = "District Schedule",
        description = "Retrieves admission schedule details for all ITIs within a specific district. Shows merit ranges, scheduled dates/times, phases, and trade information for admission counseling.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved district schedule",
                        content = @Content(schema = @Schema(implementation = DistrictScheduleResponse.class)))
        }
    )
    @GetMapping("/district-schedule")
    public List<DistrictScheduleResponse> getDistrictSchedule(
            @Parameter(description = "District code", required = true, example = "01")
            @RequestParam String distCode) {
        return reportService.getDistrictSchedule(distCode);
    }

    // ========== 20. ALL RESOURCE ROLE ==========

    @Operation(
        summary = "All Resource Role",
        description = "Lists all registered users with their roles, assigned districts/ITIs, and contact information. Used for user management and access control administration.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user roles",
                        content = @Content(schema = @Schema(implementation = AllResourceRoleResponse.class)))
        }
    )
    @GetMapping("/all-resource-roles")
    public List<AllResourceRoleResponse> getAllResourceRoles() {
        return reportService.getAllResourceRoles();
    }

    // ========== 21. APPLICANT ADDRESS WITH MOBILE ==========

    @Operation(
        summary = "Applicant Address With Mobile",
        description = "Retrieves applicant contact information including address and mobile numbers for a selected year and district. Useful for communication and verification purposes.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved applicant contact info",
                        content = @Content(schema = @Schema(implementation = ApplicantMobileAddressResponse.class)))
        }
    )
    @GetMapping("/applicant-mobile-address")
    public List<ApplicantMobileAddressResponse> getApplicantMobileAddress(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year,
            @Parameter(description = "District code filter (e.g., 01, 02, or All for all districts)", required = false, example = "All")
            @RequestParam(required = false, defaultValue = "All") String distCode) {
        return reportService.getApplicantMobileAddress(year, distCode);
    }

    // ========== 22. DISTRICT WISE APPLICATION COUNT ==========

    @Operation(
        summary = "District Wise Application Count",
        description = "Returns district-wise count of applications with approved, rejected, and unverified statuses for a selected year. Helps track application processing status across districts.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved district-wise counts",
                        content = @Content(schema = @Schema(implementation = DistrictWiseApplicationCountResponse.class)))
        }
    )
    @GetMapping("/district-wise-application-count")
    public List<DistrictWiseApplicationCountResponse> getDistrictWiseApplicationCount(
            @Parameter(description = "Academic year (e.g., 2025, 2024)", required = true, example = "2025")
            @RequestParam String year) {
        return reportService.getDistrictWiseApplicationCount(year);
    }
}