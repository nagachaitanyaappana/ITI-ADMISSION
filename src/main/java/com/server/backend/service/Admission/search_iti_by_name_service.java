
package com.server.backend.service.Admission;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.Repository.Admission.search_iti_by_name_repository;
import com.server.backend.entity.ITI.Iti;

@Service
public class search_iti_by_name_service {

    private final search_iti_by_name_repository repository;

    public search_iti_by_name_service(search_iti_by_name_repository repository) {
        this.repository = repository;
    }

    public List<Iti> searchItiByName(String itiName) {
        return repository.searchItiByName(itiName);
    }
}