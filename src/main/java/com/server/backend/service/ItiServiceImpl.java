package com.server.backend.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.Institute.ItiDto;
import com.server.backend.DTO.Institute.ItiPatchDto;
import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.Repository.DistrictMasterRepository;
import com.server.backend.Repository.ItiRepository;
import com.server.backend.entity.Iti;

@Service
public class ItiServiceImpl implements ItiService {

    private final ItiRepository repository;
    private final DistrictMasterRepository districtMasterRepository;

    public ItiServiceImpl(ItiRepository repository, DistrictMasterRepository districtMasterRepository) {
        this.repository = repository;
        this.districtMasterRepository = districtMasterRepository;
    }

    @Override
    public Iti createIti(ItiDto dto) {
        if (repository.existsById(dto.getItiCode())) {
            throw new RuntimeException("ITI Code already exists");
        }

        Iti iti = new Iti();
        BeanUtils.copyProperties(dto, iti);
        return repository.save(iti);
    }

    @Override
    public List<Iti> getAllItis() {
        return repository.findAll();
    }

    @Override
    public Iti getItiByCode(String itiCode) {
        return repository.findById(itiCode).orElse(null);
    }

    @Override
    public List<DistrictOptionResponse> getDistrictOptions() {
        return districtMasterRepository.findDistrictOptions();
    }

    @Override
    public Iti updateIti(String itiCode, ItiDto dto) {
        Iti iti = repository.findById(itiCode)
                .orElseThrow(() -> new RuntimeException("ITI Not Found"));

        BeanUtils.copyProperties(dto, iti);
        iti.setItiCode(itiCode);
        return repository.save(iti);
    }

    @Override
    public void deleteIti(String itiCode) {
        repository.deleteById(itiCode);
    }
    @Override
    public Iti getItiByCodeAndDistCode(String itiCode,
                                   String distCode) {

    return repository
            .findByItiCodeAndDistCode(itiCode, distCode)
            .orElseThrow(() ->
                    new RuntimeException("ITI not found"));
     }

     @Override
public Iti patchIti(String itiCode,
                    String distCode,
                    ItiPatchDto dto) {

    Iti iti = repository
            .findByItiCodeAndDistCode(itiCode, distCode)
            .orElseThrow(() ->
                    new RuntimeException("ITI not found"));

    if (dto.getItiName() != null)
        iti.setItiName(dto.getItiName());

    if (dto.getGovt() != null)
        iti.setGovt(dto.getGovt());

    if (dto.getDistCode() != null)
        iti.setDistCode(dto.getDistCode());

    if (dto.getItiNoniti() != null)
        iti.setItiNoniti(dto.getItiNoniti());

    if (dto.getCapacity() != null)
        iti.setCapacity(dto.getCapacity());

    if (dto.getTotStrength() != null)
        iti.setTotStrength(dto.getTotStrength());

    if (dto.getAddress() != null)
        iti.setAddress(dto.getAddress());

    if (dto.getCityTown() != null)
        iti.setCityTown(dto.getCityTown());

    if (dto.getMandCode() != null)
        iti.setMandCode(dto.getMandCode());

    if (dto.getPinCode() != null)
        iti.setPinCode(dto.getPinCode());

    if (dto.getEmail() != null)
        iti.setEmail(dto.getEmail());

    if (dto.getPrincipalName() != null)
        iti.setPrincipalName(dto.getPrincipalName());

    if (dto.getDescription() != null)
        iti.setDescription(dto.getDescription());

    if (dto.getMobile() != null)
        iti.setMobile(dto.getMobile());

    if (dto.getLandlineNumber() != null)
        iti.setLandlineNumber(dto.getLandlineNumber());

    if (dto.getYearEst() != null)
        iti.setYearEst(dto.getYearEst());

    if (dto.getItiType() != null)
        iti.setItiType(dto.getItiType());

    if (dto.getAppCode() != null)
        iti.setAppCode(dto.getAppCode());

    if (dto.getVtp() != null)
        iti.setVtp(dto.getVtp());

    if (dto.getVtpRegno() != null)
        iti.setVtpRegno(dto.getVtpRegno());

    if (dto.getLand() != null)
        iti.setLand(dto.getLand());

    if (dto.getBuiltupArea() != null)
        iti.setBuiltupArea(dto.getBuiltupArea());

    if (dto.getNoofToilets() != null)
        iti.setNoofToilets(dto.getNoofToilets());

    if (dto.getAvailableDrinkingwater() != null)
        iti.setAvailableDrinkingwater(dto.getAvailableDrinkingwater());

    if (dto.getNoofLabs() != null)
        iti.setNoofLabs(dto.getNoofLabs());

    if (dto.getNoofClassrooms() != null)
        iti.setNoofClassrooms(dto.getNoofClassrooms());

    if (dto.getExamconductingStrength() != null)
        iti.setExamconductingStrength(dto.getExamconductingStrength());

    if (dto.getDgetItiCode() != null)
        iti.setDgetItiCode(dto.getDgetItiCode());

    return repository.save(iti);
 }
}
    