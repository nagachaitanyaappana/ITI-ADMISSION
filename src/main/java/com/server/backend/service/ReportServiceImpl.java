package com.server.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictNameResponse;
import com.server.backend.DTO.Reports.ITINameResponse;
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
}
