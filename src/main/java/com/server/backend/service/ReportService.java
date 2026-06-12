package com.server.backend.service;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictNameResponse;
import com.server.backend.DTO.Reports.ITINameResponse;
import com.server.backend.DTO.Reports.ItiTradeRequest;
import com.server.backend.DTO.Reports.ItiTradeResponse;

public interface ReportService {
    DistrictNameResponse getDistricts();

    ITINameResponse getItiNamesByDistrict(DistrictCollegeTypeResponse districtCollegeType);

    ItiTradeResponse getTradesByItiName(ItiTradeRequest request);
}
