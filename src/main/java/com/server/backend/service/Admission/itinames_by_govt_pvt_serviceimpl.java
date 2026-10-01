package com.server.backend.service.Admission;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.Repository.ITI.ItiRepository;
import com.server.backend.entity.Iti;

@Service
public class itinames_by_govt_pvt_serviceimpl implements itinames_by_govt_pvt_service {

    private final ItiRepository itiRepository;

    public itinames_by_govt_pvt_serviceimpl(ItiRepository itiRepository) {
        this.itiRepository = itiRepository;
    }

    @Override
    public List<Iti> getItiNamesByGovt(String govt) {
        return itiRepository.findByGovt(govt);
    }
}
