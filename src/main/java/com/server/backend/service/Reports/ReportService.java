package com.server.backend.service.Reports;

import java.util.List;

import com.server.backend.DTO.Reports.AdmissionReportResponse;
import com.server.backend.DTO.Reports.AllResourceRoleResponse;
import com.server.backend.DTO.Reports.ApiDashboardResponse;
import com.server.backend.DTO.Reports.ApplicantCountResponse;
import com.server.backend.DTO.Reports.ApplicantMobileAddressResponse;
import com.server.backend.DTO.Reports.ApplicantReportResponse;
import com.server.backend.DTO.Reports.CasteWiseAdmissionsResponse;
import com.server.backend.DTO.Reports.CollegeWiseOpenSeatsResponse;
import com.server.backend.DTO.Reports.DistrictScheduleResponse;
import com.server.backend.DTO.Reports.DistrictWiseApplicationCountResponse;
import com.server.backend.DTO.Reports.DscFullReportResponse;
import com.server.backend.DTO.Reports.GovtPvtSeatsAbstractResponse;
import com.server.backend.DTO.Reports.ITIAdmissionsReportResponse;
import com.server.backend.DTO.Reports.ItiWiseStatusResponse;
import com.server.backend.DTO.Reports.MetadataResponse;
import com.server.backend.DTO.Reports.OpenSeatsAbstractResponse;
import com.server.backend.DTO.Reports.PhaseWiseReportResponse;
import com.server.backend.DTO.Reports.ShiftUnitResponse;
import com.server.backend.DTO.Reports.StateDashboardResponse;
import com.server.backend.DTO.Reports.StrengthFilledSeatsResponse;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse;
import com.server.backend.DTO.Reports.StudentListResponse;
import com.server.backend.DTO.Reports.TodayScheduleResponse;
import com.server.backend.DTO.Reports.TradeDurationSeatsResponse;
import com.server.backend.DTO.Reports.TradeWiseReportResponse;
import com.server.backend.DTO.Reports.TradeWiseVacantResponse;
import com.server.backend.DTO.Reports.VerifiedApplicationCountResponse;

public interface ReportService {

    // Metadata
    MetadataResponse getMetadata();

    // 1. Phase Wise Abstract
    List<PhaseWiseReportResponse> getPhaseWiseReport(String year);

    // 2. Open Seats Abstract (District wise)
    List<OpenSeatsAbstractResponse> getOpenSeatsAbstract(String year);

    // 3. College Wise Open Seats
    List<CollegeWiseOpenSeatsResponse> getCollegeWiseOpenSeats(String year, String distCode);

    // 4. Trade Duration Seats Abstract
    List<TradeDurationSeatsResponse> getTradeDurationSeats(String year, String durationMonths, String itiType);

    // 5. Admission Report (Trade wise)
    List<AdmissionReportResponse> getAdmissionReport(String year, String caste, String pwd);

    // 6. Applicant Count Nodal
    ApplicantCountResponse getApplicantCount(String year, String phase);

    // 7. ITI Admissions Report
    List<ITIAdmissionsReportResponse> getITIAdmissionsReport(String year, String distCode, String govt, String caste, String gender, String ncvtScvt, int page, int size);

    // 8. DSC Full Report
    DscFullReportResponse getDscFullReport(String distCode, String itiCode, String tradeCode, String phase, String year, String modeAdm);

    // 9. API Dashboard (District)
    List<ApiDashboardResponse> getApiDashboard(String year);

    // 10. Student Complete Details
    StudentCompleteDetailsResponse getStudentCompleteDetails(String regid, String admNum);

    // 11. Caste Wise Admissions Abstract
    List<CasteWiseAdmissionsResponse> getCasteWiseAdmissions(String year, String distCode, String govt, String phase, String gender);

    // 12. Verified Application Count
    List<VerifiedApplicationCountResponse> getVerifiedApplicationCount(String year, String distCode);

    // 13. Permitted Shift Unit Report
    List<ShiftUnitResponse> getPermittedShiftUnit(String distCode, String itiCode, int page, int size);

    // 14. Applicant Report by Phase
    List<ApplicantReportResponse> getApplicantReportByPhase(String phase, String year, String itiCode, String distCode, int page, int size);

    // 15. ITI Wise Status Report
    List<ItiWiseStatusResponse> getItiWiseStatus(String year, String distCode, String itiCode, int page, int size);

    // 16. ITI Student List
    List<StudentListResponse> getItiStudentList(String year, String itiCode, String distCode, int page, int size);

    // 17. Trade Wise Report
    List<TradeWiseReportResponse> getTradeWiseReport(String year, String distCode, String itiType);

    // 18. Today Schedule ITIs
    List<TodayScheduleResponse> getTodaySchedule();

    // 19. District Schedule
    List<DistrictScheduleResponse> getDistrictSchedule(String distCode, int page, int size);

    // 20. All Resource Role
    List<AllResourceRoleResponse> getAllResourceRoles(int page, int size);

    // 21. Applicant Address With Mobile
    List<ApplicantMobileAddressResponse> getApplicantMobileAddress(String year, String distCode, int page, int size);

    // 22. District Wise Application Count
    List<DistrictWiseApplicationCountResponse> getDistrictWiseApplicationCount(String year);

    // === NEW 4 Reports (Legacy #9, #16, #23, #25) ===

    // #9 - State Dashboard (Jdgetdasboardreport.jsp) - Roles: 10, 1
    List<StateDashboardResponse> getStateDashboard(String year, String govt);

    // #16 - Govt/Pvt District Wise Seats Abstract (govt_pvt_dist_seats_abstract.jsp) - Roles: 10, 1
    List<GovtPvtSeatsAbstractResponse> getGovtPvtSeatsAbstract(String year);

    // #23 - DistWise Strength+Filled Seats Abstract (Seats_Abstract_Strength_filled_Interface.jsp) - Role: 1
    List<StrengthFilledSeatsResponse> getStrengthFilledSeatsAbstract(String year, String distCode);

    // #25 - TradeWise Vacant Position (tradewise_vacant_position.jsp) - Role: 1
    List<TradeWiseVacantResponse> getTradeWiseVacantPositions(String year, String distCode);
}
