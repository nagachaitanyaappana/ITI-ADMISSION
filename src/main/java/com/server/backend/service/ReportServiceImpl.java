package com.server.backend.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictNameResponse;
import com.server.backend.DTO.Reports.ITINameResponse;
import com.server.backend.DTO.Reports.ItiTradeRequest;
import com.server.backend.DTO.Reports.ItiTradeResponse;
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
    public DistrictNameResponse getDistricts() {
        List<String> districtNames = districtMasterRepository.findAllNames();

        DistrictNameResponse names = new DistrictNameResponse();
        names.setDistrictName(districtNames);

        return names;
    }

    @Override
    public ITINameResponse getItiNamesByDistrict(DistrictCollegeTypeResponse districtCollegeType) {
        String dist = districtCollegeType.getDist();
        String type = districtCollegeType.getType();

        String distCode = districtMasterRepository.findCodeByDistrictName(dist);
        List<String> itiNames;

        if ("govt".equalsIgnoreCase(type)) {
            itiNames = itiRepository.findItiNamesByDistrictCodeAndGovt(distCode, "G");
        } else if ("private".equalsIgnoreCase(type)) {
            itiNames = itiRepository.findItiNamesByDistrictCodeAndGovt(distCode, "P");
        } else {
            itiNames = itiRepository.findItiNamesByDistrictCode(distCode);
        }

        ITINameResponse response = new ITINameResponse();
        response.setItiNames(itiNames);

        return response;
    }

    @Override
    public ItiTradeResponse getTradesByItiName(ItiTradeRequest request) {
        ItiTradeResponse response = new ItiTradeResponse();
        if (request != null && request.getItiName() != null) {
            String itiCode = itiRepository.findCodeByItiName(request.getItiName());
            if (itiCode != null) {
                List<String> trades = itiRepository.findTradeShortsByItiCode(itiCode);
                response.setTrades(trades);
            }
        }
        return response;
    }

    @Override
    public List<ItiWithTradesResponse> getItisWithTradesByDistrict(DistrictCollegeTypeResponse request) {
        List<ItiWithTradesResponse> responseList = new ArrayList<>();
        if (request == null || request.getDist() == null) {
            return responseList;
        }

        String dist = request.getDist();
        String type = request.getType();
        String distCode = districtMasterRepository.findCodeByDistrictName(dist);
        if (distCode == null) {
            return responseList;
        }

        List<Object[]> results;
        if ("govt".equalsIgnoreCase(type)) {
            results = itiRepository.findItiAndTradeNamesByDistrictCodeAndGovt(distCode, "G");
        } else if ("private".equalsIgnoreCase(type)) {
            results = itiRepository.findItiAndTradeNamesByDistrictCodeAndGovt(distCode, "P");
        } else {
            results = itiRepository.findItiAndTradeNamesByDistrictCode(distCode);
        }

        Map<String, List<String>> itiTradesMap = new LinkedHashMap<>();
        for (Object[] row : results) {
            String itiName = (String) row[0];
            String tradeName = (String) row[1];
            List<String> trades = itiTradesMap.computeIfAbsent(itiName, k -> new ArrayList<>());
            if (tradeName != null) {
                trades.add(tradeName);
            }
        }

        for (Map.Entry<String, List<String>> entry : itiTradesMap.entrySet()) {
            ItiWithTradesResponse item = new ItiWithTradesResponse();
            item.setItiName(entry.getKey());
            item.setTrades(entry.getValue());
            responseList.add(item);
        }

        return responseList;
    }
}
