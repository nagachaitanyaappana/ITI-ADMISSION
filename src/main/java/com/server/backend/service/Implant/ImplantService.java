package com.server.backend.service.Implant;
import com.server.backend.DTO.Implant.ImplantCreateRequest;
import com.server.backend.DTO.Implant.IndustryMappingRequest;
import com.server.backend.DTO.Implant.ImplantResponse;
import com.server.backend.DTO.Implant.InplantDashboardResponse;
import java.util.List;
import java.util.Map;
import com.server.backend.DTO.Implant.ImplantReportResponse;
public interface ImplantService {
    InplantDashboardResponse getInplantDashboardDetails();
    ImplantResponse createImplant(ImplantCreateRequest request);

    List<ImplantResponse> getAllImplants();
   
    ImplantResponse getImplantById(Long implantId);
    List<Object[]> getItis();
    List<Object[]> getIndustries(Integer itiCode);
    List<ImplantReportResponse> getReport(String itiCode);
    List<ImplantReportResponse> getNodalReport();
    List<Object[]> getDistrictItis(String distCode);
    List<ImplantReportResponse> getDistrictReport(String itiCode, Integer industryId);
    List<Map<String, Object>> getStates();
    List<Map<String, Object>> getDistrictsByState(String stateCode);
    ImplantResponse updateImplant(Long implantId, ImplantCreateRequest request);
    void deleteImplant(Long implantId);

    // ========== ITI - INDUSTRY MAPPING ==========
    Map<String, Object> getMappingMasters();
    List<Map<String, Object>> getMappings(Integer itiCode);
    Map<String, Object> getMappingBySlno(Long slno);
    Map<String, Object> saveMapping(Integer itiCode, IndustryMappingRequest request);
    Map<String, Object> updateMapping(Long slno, IndustryMappingRequest request);
    void deleteMapping(Long slno);

    // ========== NODAL MAPPING LOOKUPS ==========
    List<Map<String, Object>> getMappingDistricts();
    List<Map<String, Object>> getMappingItis(String distCode);
    List<Map<String, Object>> getNodalMappingReport();
    Map<String, Object> getTraineesCounts();
    List<Map<String, Object>> getTraineesByType(String type);
    List<ImplantReportResponse> getDatewiseReport(String fromDate, String toDate);
    List<Map<String, Object>> getYearwiseReport(int year, String itiType);
}
