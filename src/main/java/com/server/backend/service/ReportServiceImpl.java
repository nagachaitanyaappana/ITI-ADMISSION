package com.server.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictMasterResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse.TradeDetail;
import com.server.backend.Repository.DistrictMasterRepository;
import com.server.backend.Repository.ItiRepository;

@Service
public class ReportServiceImpl implements ReportService {

    private final DistrictMasterRepository districtMasterRepository;
    private final ItiRepository itiRepository;

    public ReportServiceImpl(DistrictMasterRepository districtMasterRepository, ItiRepository itiRepository) {
        this.districtMasterRepository = districtMasterRepository;
        this.itiRepository = itiRepository;
    }

    @Override
    public List<DistrictMasterResponse> getAllDistricts() {
        return districtMasterRepository.findAllDistrictsDTO();
    }

    @Override
    public List<ItiWithTradesResponse> getItisWithTradesByDistrict(DistrictCollegeTypeResponse request) {
        List<ItiWithTradesResponse> responseList = new ArrayList<>();
        if (request == null || request.getDist() == null)
            return responseList;

        String distCode = request.getDist();
        String type = request.getType();

        List<Object[]> results;
        if ("G".equalsIgnoreCase(type) || "P".equalsIgnoreCase(type)) {
            results = itiRepository.findItiAndTradeNamesByDistrictCodeAndGovt(distCode, type);
        } else {
            results = itiRepository.findItiAndTradeNamesByDistrictCode(distCode);
        }

        Map<String, ItiWithTradesResponse> itiMap = new java.util.LinkedHashMap<>();

        for (Object[] row : results) {
            String itiCode = (String) row[0];
            String itiName = (String) row[1];
            String tradeName = (String) row[2];
            Number strengthNum = (Number) row[3];

            ItiWithTradesResponse item = itiMap.computeIfAbsent(itiCode, code -> {
                ItiWithTradesResponse response = new ItiWithTradesResponse();
                response.setCode(code);
                response.setItiName(itiName);
                response.setTrades(new ArrayList<>());
                return response;
            });

            if (tradeName != null) {
                item.getTrades().add(new TradeDetail(tradeName, strengthNum != null ? strengthNum.intValue() : 0));
            }
        }

        responseList.addAll(itiMap.values());
        return responseList;
    }
}
