package com.server.backend.service.Reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.DTO.Reports.ItiTradeDisplayResponse;
import com.server.backend.DTO.Reports.ItiTradeDisplayResponse.TradeDetail;
import com.server.backend.DTO.Reports.TradeDisplayReportRequest;
import com.server.backend.Repository.DistrictMasterRepository;
import com.server.backend.Repository.ItiRepository;

@Service
public class TradeDisplayReportServiceImpl implements TradeDisplayReportService {

    private final DistrictMasterRepository districtMasterRepository;
    private final ItiRepository itiRepository;

    public TradeDisplayReportServiceImpl(DistrictMasterRepository districtMasterRepository, ItiRepository itiRepository) {
        this.districtMasterRepository = districtMasterRepository;
        this.itiRepository = itiRepository;
    }

    @Override
    public List<DistrictOptionResponse> getAllDistricts() {
        return districtMasterRepository.findAllDistrictsDTO();
    }

    @Override
    public List<ItiTradeDisplayResponse> getItisWithTradesByDistrict(TradeDisplayReportRequest request) {
        List<ItiTradeDisplayResponse> responseList = new ArrayList<>();
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

        Map<String, ItiTradeDisplayResponse> itiMap = new java.util.LinkedHashMap<>();

        for (Object[] row : results) {
            String itiCode = (String) row[0];
            String itiName = (String) row[1];
            String tradeName = (String) row[2];
            Number strengthNum = (Number) row[3];

            ItiTradeDisplayResponse item = itiMap.computeIfAbsent(itiCode, code -> {
                ItiTradeDisplayResponse response = new ItiTradeDisplayResponse();
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
