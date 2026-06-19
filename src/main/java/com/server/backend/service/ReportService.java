package com.server.backend.service;

import java.util.List;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictMasterResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;

public interface ReportService {
    // We will return a List of objects that contain both the name and the code
    List<DistrictMasterResponse> getAllDistricts(); 

    List<ItiWithTradesResponse> getItisWithTradesByDistrict(DistrictCollegeTypeResponse request);
}