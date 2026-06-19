package com.server.backend.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictMasterResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.DTO.Reports.TradeDetail;
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
        if (request == null || request.getDist() == null) return responseList;

        String distCode = request.getDist(); 
        String type = request.getType();

        List<Object[]> results;
        if ("G".equalsIgnoreCase(type) || "P".equalsIgnoreCase(type)) {
            results = itiRepository.findItiAndTradeNamesByDistrictCodeAndGovt(distCode, type);
        } else {
            results = itiRepository.findItiAndTradeNamesByDistrictCode(distCode);
        }

        // Logic to populate itiTradesMap
        Map<String, List<TradeDetail>> itiTradesMap = new LinkedHashMap<>();
        
        for (Object[] row : results) {
            String itiName = (String) row[0];
            String tradeName = (String) row[1];
            Number strengthNum = (Number) row[2];
            int strength = (strengthNum != null) ? strengthNum.intValue() : 0;

            itiTradesMap.computeIfAbsent(itiName, k -> new ArrayList<>())
                        .add(new TradeDetail(tradeName, strength));
        }

        for (Map.Entry<String, List<TradeDetail>> entry : itiTradesMap.entrySet()) {
            ItiWithTradesResponse item = new ItiWithTradesResponse();
            item.setItiName(entry.getKey());
            item.setTrades(entry.getValue());
            responseList.add(item);
        }
        
        return responseList;
    }
}