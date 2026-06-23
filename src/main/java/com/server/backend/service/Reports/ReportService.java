package com.server.backend.service.Reports;

import java.util.List;

import com.server.backend.DTO.Reports.AdmissionReportResponse;
import com.server.backend.DTO.Reports.ApiDashboardResponse;
import com.server.backend.DTO.Reports.ApplicantCountResponse;
import com.server.backend.DTO.Reports.CollegeWiseOpenSeatsResponse;
import com.server.backend.DTO.Reports.DscFullReportResponse;
import com.server.backend.DTO.Reports.ITIAdmissionsReportResponse;
import com.server.backend.DTO.Reports.MetadataResponse;
import com.server.backend.DTO.Reports.OpenSeatsAbstractResponse;
import com.server.backend.DTO.Reports.PhaseWiseReportResponse;
import com.server.backend.DTO.Reports.StudentCompleteDetailsResponse;
import com.server.backend.DTO.Reports.TradeDurationSeatsResponse;

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
    List<ITIAdmissionsReportResponse> getITIAdmissionsReport(String year, String distCode, String govt, String caste, String gender, String ncvtScvt, String limitRows);

    // 8. DSC Full Report
    DscFullReportResponse getDscFullReport(String distCode, String itiCode, String tradeCode, String phase, String year, String modeAdm);

    // 9. API Dashboard
    List<ApiDashboardResponse> getApiDashboard(String year);

    // 10. Student Complete Details
    StudentCompleteDetailsResponse getStudentCompleteDetails(String regid, String admNum);
}