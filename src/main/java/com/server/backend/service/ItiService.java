package com.server.backend.service;
import java.util.List;
import com.server.backend.DTO.Reports.ItiDto;
import com.server.backend.entity.Iti;

public interface ItiService {
     Iti createIti(ItiDto dto);

    List<Iti> getAllItis();

    Iti getItiByCode(String itiCode);

    Iti updateIti(String itiCode, ItiDto dto);

    void deleteIti(String itiCode);
    
}
