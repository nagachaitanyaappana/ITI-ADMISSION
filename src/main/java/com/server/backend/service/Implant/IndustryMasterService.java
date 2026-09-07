package com.server.backend.service.Implant;

import com.server.backend.DTO.Implant.IndustryMasterRequest;
import com.server.backend.DTO.Implant.IndustryMasterResponse;
import java.util.List;
public interface IndustryMasterService {

    IndustryMasterResponse createIndustry(IndustryMasterRequest request);
    List<IndustryMasterResponse> getAllIndustries();
    IndustryMasterResponse getIndustryById(Long industryId);
    IndustryMasterResponse updateIndustry( Long industryId, IndustryMasterRequest request);
    void deleteIndustry(Long industryId);
}
