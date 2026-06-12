package com.server.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.Institute.ItiDto;
import com.server.backend.entity.iti;
import com.server.backend.Repository.ItiRepository;


@Service
public class ItiServiceImpl implements ItiService {

    @Autowired
    private ItiRepository repository;

    @Override
    public iti createIti(ItiDto dto) {

        iti iti1 = new iti();

        iti1.setItiCode(dto.getItiCode());
        iti1.setItiName(dto.getItiName());

        return repository.save(iti1);
    }

    @Override
    public List<iti> getAllItis() {
        return repository.findAll();
    }

    @Override
    public iti getItiByCode(String itiCode) {
        return repository.findById(itiCode)
                .orElse(null);
    }

    @Override
    public iti updateIti(String itiCode, ItiDto dto) {

        iti iti1 = repository.findById(itiCode).orElse(null);

        if (iti1 != null) {
            iti1.setItiName(dto.getItiName());
            return repository.save(iti1);
        }

        return null;
    }

    @Override
    public void deleteIti(String itiCode) {
        repository.deleteById(itiCode);
    }
}