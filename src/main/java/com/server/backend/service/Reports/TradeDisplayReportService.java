package com.server.backend.service.Reports;

import java.util.List;

import com.server.backend.DTO.DistrictOptionResponse;
import com.server.backend.DTO.ItiListResponse;
import com.server.backend.DTO.ItiTradeDisplayResponse;
import com.server.backend.DTO.TradeDisplayReportRequest;

public interface TradeDisplayReportService {
    List<DistrictOptionResponse> getDistrictOptions();

    List<ItiTradeDisplayResponse> getTradeDisplayReport(TradeDisplayReportRequest request);

    List<ItiListResponse> getItiList(String govt);
}
