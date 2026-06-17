package com.server.backend.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.server.backend.DTO.Institute.ItiDto;
import com.server.backend.entity.Iti;
import com.server.backend.Repository.ItiRepository;


@Service
public class ItiServiceImpl implements ItiService {

    
    private ItiRepository repository;

   @Override
public Iti createIti(ItiDto dto) {

    if(repository.existsById(dto.getItiCode())) {
        throw new RuntimeException(
                "ITI Code already exists");
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
}