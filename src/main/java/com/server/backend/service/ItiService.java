package com.server.backend.service;
import java.util.List;
import com.server.backend.DTO.Institute.ItiDto;
import com.server.backend.entity.iti;

public interface ItiService {
     iti createIti(ItiDto dto);

    List<iti> getAllItis();

    iti getItiByCode(String itiCode);

    iti updateIti(String itiCode, ItiDto dto);

    void deleteIti(String itiCode);
    
}
