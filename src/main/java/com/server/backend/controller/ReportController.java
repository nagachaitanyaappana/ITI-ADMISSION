package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictNameResponse;
import com.server.backend.DTO.Reports.ITINameResponse;
import com.server.backend.Repository.ItiRepository;
import com.server.backend.Repository.DistrictMasterRepository;

@RestController
public class ReportController {

    private final DistrictMasterRepository districtmasterrepo;
    private final ItiRepository itiRepository;
    public ReportController(DistrictMasterRepository districtmasterrepo,ItiRepository itiRepository) {
    this.districtmasterrepo = districtmasterrepo;
        this.itiRepository = itiRepository;
    }

    @GetMapping("/districts")
    public DistrictNameResponse getDistricts() {
        List<String> districtNames = districtmasterrepo.findAllNames();

        DistrictNameResponse names = new DistrictNameResponse();
        names.setDistrictName(districtNames);

        return names;
    }

    @PostMapping("/districts/code")
        public ITINameResponse getItiNamesByDistrict(@RequestBody DistrictCollegeTypeResponse districtCollegeType) {

        String dist = districtCollegeType.getDist();
        String type = districtCollegeType.getType();

        String dist_code = districtmasterrepo.findCodeByDistrictName(dist);
        List<String> iti_names ;

        if(type.equalsIgnoreCase("govt"))
            iti_names =   itiRepository.findItiNamesByDistrictCodeAndGovt(dist_code, "G");
        else if(type.equalsIgnoreCase("private"))
            iti_names =   itiRepository.findItiNamesByDistrictCodeAndGovt(dist_code, "P");
        else
            iti_names =   itiRepository.findItiNamesByDistrictCode(dist_code);

        ITINameResponse response = new ITINameResponse();
        response.setItiNames(iti_names);//ignore

        return response;
    }
        
    @GetMapping("/AboutStrive")
    public String aboutStrive() {
        return "About Strive.";
    }

    @GetMapping("/DisclosureManagement")
    public String disclosureManagement() {
        return "Disclosure Management.";
    }
    
}