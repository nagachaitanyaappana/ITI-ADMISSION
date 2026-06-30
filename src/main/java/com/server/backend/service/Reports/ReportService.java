package com.server.backend.service.Reports;

import java.util.List;

import com.server.backend.DTO.Reports.AdmissionReportResponse;
import com.server.backend.DTO.Reports.AllResourceRoleResponse;
import com.server.backend.DTO.Reports.ApiDashboardResponse;
import com.server.backend.DTO.Reports.ApplicantMobileAddressResponse;
import com.server.backend.DTO.Reports.ApplicantReportResponse;
import com.server.backend.DTO.Reports.CasteWiseAdmissionsResponse;
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

public interface ReportService {

    // 1. ITI Wise Status Report
    List<ItiWiseStatusResponse> getItiWiseStatus(String year, String distCode, String itiCode, int page, int size);

    // 2. Applicant Report by Phase
    List<ApplicantReportResponse> getApplicantReportByPhase(String phase, String year, String itiCode, String distCode, int page, int size);

    // 3. Admission Report (Trade wise)
    List<AdmissionReportResponse> getAdmissionReport(String year, String caste, String pwd);

    // 4. DSC Full Report
    DscFullReportResponse getDscFullReport(String distCode, String itiCode, String tradeCode, String phase, String year, String modeAdm);

    // 5. Caste Wise Admissions Abstract
    List<CasteWiseAdmissionsResponse> getCasteWiseAdmissions(String year, String distCode, String govt, String phase, String gender);

    // 6. Applicant Address With Mobile
    List<ApplicantMobileAddressResponse> getApplicantMobileAddress(String year, String distCode, int page, int size);

    // 7. API Dashboard (District)
    List<ApiDashboardResponse> getApiDashboard(String year);

    // 8. Verified Application Count
    List<VerifiedApplicationCountResponse> getVerifiedApplicationCount(String year, String distCode);

    // 9. State Dashboard
    List<StateDashboardResponse> getStateDashboard(String year, String govt);

    // 10. Phase Wise Abstract
    List<PhaseWiseReportResponse> getPhaseWiseReport(String year);

    // 11. Today Schedule ITIs
    List<TodayScheduleResponse> getTodaySchedule();

    // 12. Trade Wise Report
    List<TradeWiseReportResponse> getTradeWiseReport(String year, String distCode, String itiType);

    // 13. Applicant Report by Phase (State Wise - same as #2)
    // Note: Uses same method as #2

    // 14. Open Seats Abstract (District wise)
    List<OpenSeatsAbstractResponse> getOpenSeatsAbstract(String year);

    // 15. Trade Duration Seats Abstract
    List<TradeDurationSeatsResponse> getTradeDurationSeats(String year, String durationMonths, String itiType);

    // 16. Govt/Pvt District Wise Seats Abstract
    List<GovtPvtSeatsAbstractResponse> getGovtPvtSeatsAbstract(String year);

    // 17. Student Complete Details
    StudentCompleteDetailsResponse getStudentCompleteDetails(String regid, String admNum);

    // 18. District Wise Application Count
    List<DistrictWiseApplicationCountResponse> getDistrictWiseApplicationCount(String year);

    // 19. District Schedule
    List<DistrictScheduleResponse> getDistrictSchedule(String distCode, int page, int size);

    // 20. Permitted Shift Unit Report
    List<ShiftUnitResponse> getPermittedShiftUnit(String distCode, String itiCode, int page, int size);

    // 21. ITI Admissions Report
    List<ITIAdmissionsReportResponse> getITIAdmissionsReport(String year, String distCode, String govt, String caste, String gender, String ncvtScvt, int page, int size);

    // 22. All Resource Role
    List<AllResourceRoleResponse> getAllResourceRoles(int page, int size);

    // 23. DistWise Strength+Filled Seats Abstract
    List<StrengthFilledSeatsResponse> getStrengthFilledSeatsAbstract(String year, String distCode);

    // 24. Admission Report (Trade wise - same as #3)
    // Note: Uses same method as #3

    // 25. TradeWise Vacant Position
    List<TradeWiseVacantResponse> getTradeWiseVacantPositions(String year, String distCode);
}
