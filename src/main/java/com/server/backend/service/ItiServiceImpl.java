package com.server.backend.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.server.backend.DTO.Institute.ItiDto;
import com.server.backend.entity.Iti;
import com.server.backend.entity.dist_master;
import com.server.backend.Repository.ItiRepository;
import com.server.backend.Repository.DistrictMasterRepository;
@Service
public class ItiServiceImpl implements ItiService {

    
    private final ItiRepository repository;
    private final DistrictMasterRepository districtRepo;

    public ItiServiceImpl(ItiRepository repository, DistrictMasterRepository districtRepo) {
        this.repository = repository;
        this.districtRepo = districtRepo;
    }

    @Override
public Iti createIti(ItiDto dto) {

    if(repository.existsById(dto.getItiCode())) {
        throw new RuntimeException("ITI code already exists");
    }

    Iti iti = new Iti();

    BeanUtils.copyProperties(dto, iti);
    

    if(dto.getDistrictName()!=null) {

        dist_master district =
                districtRepo.findByDistname(dto.getDistrictName())
                .orElseThrow(() ->
                        new RuntimeException("District not found"));

        iti.setDistCode(district.getDistcode());
    }

    return repository.save(iti);
}
    @Override
    public List<Iti> getAllItis() {
        return repository.findAll();
    }

    @Override
    public Iti getItiByCode(String itiCode) {
        return repository.findById(itiCode)
                .orElse(null);
    }

   @Override
public Iti updateIti(String itiCode, ItiDto dto) {

    Iti iti = repository.findById(itiCode)
            .orElseThrow(() ->
                    new RuntimeException(
                            "ITI Not Found"));

    BeanUtils.copyProperties(dto, iti);

    iti.setItiCode(itiCode);

    return repository.save(iti);
}

     @Override
    public void deleteIti(String itiCode) {
        repository.deleteById(itiCode);
    }

   @Override
public Iti patchIti(String itiCode, ItiDto dto) {

    Iti iti = repository.findById(itiCode)
            .orElseThrow(() -> new RuntimeException("ITI Not Found"));

    if (dto.getItiName() != null)
        iti.setItiName(dto.getItiName());

    if (dto.getGovt() != null)
        iti.setGovt(dto.getGovt());

    if (dto.getDistCode() != null)
        iti.setDistCode(dto.getDistCode());

    if (dto.getCapacity() != null)
        iti.setCapacity(dto.getCapacity());

    if (dto.getAllocated() != null)
        iti.setAllocated(dto.getAllocated());

    if (dto.getRemainingCapacity() != null)
        iti.setRemainingCapacity(dto.getRemainingCapacity());

    if (dto.getTotStrength() != null)
        iti.setTotStrength(dto.getTotStrength());

    if (dto.getAddress() != null)
        iti.setAddress(dto.getAddress());

    if (dto.getCityTown() != null)
        iti.setCityTown(dto.getCityTown());

    if (dto.getEmail() != null)
        iti.setEmail(dto.getEmail());

    if (dto.getPrincipalName() != null)
        iti.setPrincipalName(dto.getPrincipalName());

    if (dto.getMobile() != null)
        iti.setMobile(dto.getMobile());

    if (dto.getWebsite() != null)
        iti.setWebsite(dto.getWebsite());

    if (dto.getNoofLabs() != null)
        iti.setNoofLabs(dto.getNoofLabs());

    if (dto.getNoofClassrooms() != null)
        iti.setNoofClassrooms(dto.getNoofClassrooms());

    if (dto.getAdmissionPermission() != null)
        iti.setAdmissionPermission(dto.getAdmissionPermission());

    return repository.save(iti);
}

}