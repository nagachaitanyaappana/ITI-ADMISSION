package com.server.backend.service.ITI;
import java.util.List;

import com.server.backend.DTO.ItiDto;
import com.server.backend.DTO.ItiPatchDto;
import com.server.backend.DTO.DistrictOptionResponse;
import com.server.backend.entity.ITI.Iti;

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

    Iti getItiByCodeAndDistCode(String itiCode, String distCode);
     List<Iti> getItisByDistrict(String distCode);
}
