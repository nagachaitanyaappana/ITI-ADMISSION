package com.server.backend.service;
import java.util.List;

import com.server.backend.DTO.Institute.ItiDto;
import com.server.backend.DTO.Institute.ItiPatchDto;
import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.entity.Iti;

public interface ItiService {
     Iti createIti(ItiDto dto);

    List<Iti> getAllItis();

    Iti getItiByCode(String itiCode);

    List<DistrictOptionResponse> getDistrictOptions();

    Iti updateIti(String itiCode, ItiDto dto);

    void deleteIti(String itiCode);
    Iti patchIti(String itiCode,
             String distCode,
             ItiPatchDto dto);

    Iti getItiByCodeAndDistCode(String itiCode,
                            String distCode);
}
