package com.server.backend.service;

import java.util.List;
import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictNameResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;

public interface ReportService {
    DistrictNameResponse getDistricts();

    List<ItiWithTradesResponse> getItisWithTradesByDistrict(DistrictCollegeTypeResponse request);
}
