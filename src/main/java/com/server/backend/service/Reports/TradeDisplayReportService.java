package com.server.backend.service.Reports;

import java.util.List;

import com.server.backend.DTO.DistrictOptionResponse;
import com.server.backend.DTO.ItiListResponse;

public interface TradeDisplayReportService {
    List<DistrictOptionResponse> getDistrictOptions();

    List<ItiListResponse> getItiList(String govt);
}
