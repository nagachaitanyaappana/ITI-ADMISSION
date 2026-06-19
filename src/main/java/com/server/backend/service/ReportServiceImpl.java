package com.server.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictMasterResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;
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
        // Returns the list of districts to be used in the dropdown
        return districtMasterRepository.findAll(); 
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
        java.util.Map<String, List<com.server.backend.DTO.Reports.TradeDetail>> itiTradesMap = new java.util.LinkedHashMap<>();
        
        for (Object[] row : results) {
            String itiName = (String) row[0];
            String tradeName = (String) row[1];
            Number strengthNum = (Number) row[2];
            int strength = (strengthNum != null) ? strengthNum.intValue() : 0;

            itiTradesMap.computeIfAbsent(itiName, k -> new ArrayList<>())
                        .add(new com.server.backend.DTO.Reports.TradeDetail(tradeName, strength));
        }

        for (java.util.Map.Entry<String, List<com.server.backend.DTO.Reports.TradeDetail>> entry : itiTradesMap.entrySet()) {
            ItiWithTradesResponse item = new ItiWithTradesResponse();
            item.setItiName(entry.getKey());
            item.setTrades(entry.getValue());
            responseList.add(item);
        }
        
        return responseList;
    }
}