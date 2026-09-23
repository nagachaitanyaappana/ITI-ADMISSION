package com.server.backend.service.Reports;

import java.util.List;

import com.server.backend.DTO.DistrictOptionResponse;
import com.server.backend.DTO.ItiListResponse;
import com.server.backend.DTO.ItiTradeDisplayResponse;

public interface TradeDisplayReportService {
    List<DistrictOptionResponse> getDistrictOptions();

    List<ItiTradeDisplayResponse> getTradeDisplayReport(String distCode, String govt);

    List<ItiListResponse> getItiList(String govt);
}
