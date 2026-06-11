package com.server.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.Reports.ItiDto;
import com.server.backend.entity.Iti;
import com.server.backend.Repository.ItiRepository;


@Service
public class ItiServiceImpl implements ItiService {

    @Autowired
    private ItiRepository repository;

    @Override
    public Iti createIti(ItiDto dto) {

        Iti iti = new Iti();

        iti.setItiCode(dto.getItiCode());
        iti.setItiName(dto.getItiName());

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
                .orElse(null);

        if (iti != null) {
            iti.setItiName(dto.getItiName());
            return repository.save(iti);
        }

        return null;
    }

    @Override
    public void deleteIti(String itiCode) {
        repository.deleteById(itiCode);
    }
}