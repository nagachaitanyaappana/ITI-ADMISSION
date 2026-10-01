package com.server.backend.service.Reports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.DistrictOptionResponse;
import com.server.backend.DTO.ItiListResponse;
import com.server.backend.Repository.ITI.DistrictMasterRepository;
import com.server.backend.Repository.ITI.ItiRepository;

@Service
public class TradeDisplayReportServiceImpl implements TradeDisplayReportService {

    private final DistrictMasterRepository districtMasterRepository;
    private final ItiRepository itiRepository;

    public TradeDisplayReportServiceImpl(DistrictMasterRepository districtMasterRepository, ItiRepository itiRepository) {
        this.districtMasterRepository = districtMasterRepository;
        this.itiRepository = itiRepository;
    }

    @Override
    public List<DistrictOptionResponse> getDistrictOptions() {
        return districtMasterRepository.findDistrictOptions();
    }

    @Override
    public List<ItiListResponse> getItiList(String govt) {
        List<ItiListResponse> responseList = new ArrayList<>();

        // Treat "A" (All) or empty value as "no filter" so every ITI is returned.
        String effectiveGovt = (govt != null && !govt.isEmpty() && !govt.equalsIgnoreCase("A"))
                ? govt : null;

        List<Object[]> results = itiRepository.findItiListRows(effectiveGovt);

        for (Object[] row : results) {
            String districtName = (String) row[0];
            String itiCode = (String) row[1];
            String ncvtCode = (String) row[2];
            String itiName = (String) row[3];
            responseList.add(new ItiListResponse(districtName, itiCode, ncvtCode, itiName));
        }

        return responseList;
    }

    @Override
    public List<com.server.backend.DTO.ItiTradeDisplayResponse> getTradeDisplayReport(String distCode, String govt) {
        List<com.server.backend.DTO.ItiTradeDisplayResponse> responseList = new ArrayList<>();
        if (distCode == null || distCode.trim().isEmpty()) {
            return responseList;
        }

        String effectiveGovt = (govt != null && !govt.isEmpty() && !govt.equalsIgnoreCase("A"))
                ? govt : null;

        List<Object[]> results = itiRepository.findTradeDisplayRowsByDistrictCodeAndGovt(distCode.trim(), effectiveGovt);
        java.util.Map<String, com.server.backend.DTO.ItiTradeDisplayResponse> itiMap = new java.util.LinkedHashMap<>();

        for (Object[] row : results) {
            String itiCode = row[0] != null ? row[0].toString() : "";
            String itiName = row[1] != null ? row[1].toString() : "";
            String mgt = row[2] != null ? row[2].toString() : "";
            String tradeName = row[3] != null ? row[3].toString() : null;
            Number strengthNum = row[4] instanceof Number ? (Number) row[4] : null;

            com.server.backend.DTO.ItiTradeDisplayResponse item = itiMap.computeIfAbsent(itiCode, code -> {
                com.server.backend.DTO.ItiTradeDisplayResponse resp = new com.server.backend.DTO.ItiTradeDisplayResponse();
                resp.setCode(code);
                resp.setItiName(itiName);
                resp.setGovt(mgt);
                resp.setTrades(new ArrayList<>());
                return resp;
            });

            if (tradeName != null) {
                item.getTrades().add(new com.server.backend.DTO.ItiTradeDisplayResponse.TradeDetail(
                    tradeName, 
                    strengthNum != null ? strengthNum.intValue() : 0
                ));
            }
        }

        responseList.addAll(itiMap.values());
        return responseList;
    }
}
