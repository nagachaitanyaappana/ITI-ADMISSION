package com.server.backend.service.Reports;

import java.util.List;

import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.DTO.Reports.ItiTradeDisplayResponse;
import com.server.backend.DTO.Reports.TradeDisplayReportRequest;

public interface TradeDisplayReportService {
    List<DistrictOptionResponse> getAllDistricts();

    List<ItiTradeDisplayResponse> getItisWithTradesByDistrict(TradeDisplayReportRequest request);
}
