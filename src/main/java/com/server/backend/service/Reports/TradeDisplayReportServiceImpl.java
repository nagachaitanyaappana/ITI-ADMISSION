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
}
