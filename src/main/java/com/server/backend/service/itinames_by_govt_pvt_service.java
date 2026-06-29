package com.server.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.Repository.itinames_by_govt_pvt_repository;

@Service
public class itinames_by_govt_pvt_service {

    private itinames_by_govt_pvt_repository repository;

    public List<String> getItiNamesByGovtPvt(String govt) {
        return repository.getItiNamesByGovtPvt(govt);
    }
}