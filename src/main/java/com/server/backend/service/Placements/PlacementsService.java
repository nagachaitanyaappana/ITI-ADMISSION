package com.server.backend.service.Placements;

import com.server.backend.DTO.Placements.PlacementsDistinctItiResponse;
import com.server.backend.DTO.Placements.PlacementsGroupedResponse;
import com.server.backend.DTO.Placements.PlacementsOverviewResponse;
import java.util.List;
import java.util.Map;

public interface PlacementsService {
    PlacementsOverviewResponse getOverviewDetails();
    PlacementsGroupedResponse getCountPlacementsGroupedByPtype();
    PlacementsDistinctItiResponse getDistinctItiCodesByPtype();

    List<Map<String, Object>> getSchedulewiseDistrictData(String year);
    List<Map<String, Object>> getSchedulewiseItiData(String year, String distCode);
    List<Map<String, Object>> getSchedulewisePlacementDetails(String plcmtId);

    List<Map<String, Object>> getDatewiseScheduleData(String fromDate, String toDate, String ptype);

    List<Map<String, Object>> getStatePlacementReport();

    List<Map<String, Object>> getDistrictPlacementReport(String distCode);

    List<Map<String, Object>> getYearwisePlacementReport();

    List<Map<String, Object>> getYearwisePlacementDetails(String year);

    List<String> getDistinctAdmissionYears();

    List<Map<String, Object>> getStateSkillDevelopmentPlanReport(String year);

    List<Map<String, Object>> getPlacementDataDetailsReport(String year, String itiType);

    // ===== Schedule Entry (visit) =====
    List<Map<String, Object>> getDistrictScheduleItis(String distCode);

    List<Map<String, Object>> getDistrictSchedules(String distCode);

    Map<String, Object> createSchedule(Map<String, Object> req);

    // ===== Dist Report (visit) =====
    List<String> getDistrictPlacementYears(String distCode);

    List<Map<String, Object>> getDistrictItis(String distCode);

    List<Map<String, Object>> getDistrictPlacementReport(String distCode, String ptype, String year, String itiCode);

    // ===== Placements Entry (ITI) =====
    Map<String, Object> getCandidateByAdmNum(String admNum, String itiCode);

    List<Map<String, Object>> findCandidatesByName(String name, String itiCode);

    List<Map<String, Object>> getCandidatePlacements(String admNum, String itiCode);

    List<Map<String, Object>> getItiSchedules(String itiCode);

    List<Map<String, Object>> getMasterTrades();

    List<Map<String, Object>> getMasterStates();

    List<Map<String, Object>> getMasterDistricts();

    Map<String, Object> createPlacement(Map<String, Object> req);

    // ===== Placements ITI Report (ITI) =====
    List<String> getItiPlacementYears(String itiCode);

    List<Map<String, Object>> getItiPlacementReport(String itiCode, String ptype, String year);
}
