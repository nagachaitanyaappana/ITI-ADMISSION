package com.server.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.server.backend.Repository.itinames_by_distcode_repository;

@Service
public class itinames_by_distcode_service {

    
    private itinames_by_distcode_repository repository;

    public List<String> getItiNamesByDistrict(String distCode) {
        return repository.getItiNamesByDistrict(distCode);
    }
}