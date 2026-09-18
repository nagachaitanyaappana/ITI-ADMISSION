package com.server.backend.service.Reports;

import java.util.List;

import com.server.backend.DTO.AdmissionReportDetailResponse;
import com.server.backend.DTO.NotAdmittedStudentResponse;
import com.server.backend.DTO.AdmissionReportResponse;
import com.server.backend.DTO.AllResourceRoleResponse;
import com.server.backend.DTO.ApiDashboardResponse;
import com.server.backend.DTO.ApplicantMobileAddressResponse;
import com.server.backend.DTO.ApplicantCountDistrictResponse;
import com.server.backend.DTO.ApplicantReportResponse;
import com.server.backend.DTO.CasteWiseAdmissionsResponse;
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
//import com.server.backend.DTO.VerifiedApplicationCountResponse;
import com.server.backend.DTO.VerifiedApplicationCountReportResponse;
import com.server.backend.DTO.DscOptionsResponse;
import com.server.backend.DTO.CurrentAdmissionPhaseResponse;

public interface ReportService {

    // 1. ITI Wise Status Report
    List<ItiWiseStatusResponse> getItiWiseStatus(String year, String distCode, String itiCode, int page, int size);

    // 2. Applicant Report by Phase
    List<ApplicantReportResponse> getApplicantReportByPhase(String phase, String year, String itiCode, String distCode, int page, int size);

    // 3. Admission Report (Trade wise - for role 1 / state level)
    List<AdmissionReportResponse> getAdmissionReport(String year, String caste, String pwd);

    // 3a. Admission Report (ITI - candidate detail for role 4)
    List<AdmissionReportDetailResponse> getAdmissionReportDetails(int page, int size);

    // 4. DSC Full Report
    DscFullReportResponse getDscFullReport(String distCode, String itiCode, String tradeCode, String phase, String year, String modeAdm);

    // 4a. DSC Options
    DscOptionsResponse getDscOptions(String distCode, String itiCode);

    // 5. Caste Wise Admissions Abstract
    List<CasteWiseAdmissionsResponse> getCasteWiseAdmissions(String year, String distCode, String govt, String phase, String gender);

    // 5a. Applicant Count District Wise (for old Caste Wise Admissions Abstract page)
    List<ApplicantCountDistrictResponse> getApplicantCountDistrictWise(String year, String distCode, String govt, String phase);

    // 6. Applicant Address With Mobile
    List<ApplicantMobileAddressResponse> getApplicantMobileAddress(String year, String distCode, int page, int size);

    // 7. API Dashboard (District - ITI level)
    List<ApiDashboardResponse> getApiDashboard(String year, String distCode);

    // 8. Verified Application Count
    VerifiedApplicationCountReportResponse getVerifiedApplicationCount(String year, String distCode);

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
    List<GovtPvtSeatsAbstractResponse> getGovtPvtSeatsAbstract(String year, String govt);

    // 17. Student Complete Details
    StudentCompleteDetailsResponse getStudentCompleteDetails(String regid, String admNum);

    // 18. District Wise Application Count
    List<DistrictWiseApplicationCountResponse> getDistrictWiseApplicationCount(String year);

    // 19. District Schedule
    List<DistrictScheduleResponse> getDistrictSchedule(String distCode, String year, int page, int size);

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

    // 26. Current Admission Phase
    CurrentAdmissionPhaseResponse getCurrentAdmissionPhase();

    // 27. Students Not Admitted
    List<String> getStudentsNotAdmittedYears();

    // 27. Students Not Admitted
    List<NotAdmittedStudentResponse> getStudentsNotAdmitted(String year, Integer phase, int page, int size);

    long countStudentsNotAdmitted(String year, Integer phase);
}
