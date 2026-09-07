package com.server.backend.service.Implant;

import com.server.backend.DTO.Implant.IndustryPartnerDetailsRequest;
import com.server.backend.DTO.Implant.IndustryPartnerDetailsResponse;

import java.util.List;

public interface IndustryPartnerDetailsService {

    IndustryPartnerDetailsResponse createDetails(
            IndustryPartnerDetailsRequest request
    );

    List<IndustryPartnerDetailsResponse> getAllDetails();

    IndustryPartnerDetailsResponse getDetailsById(Long pid);

    IndustryPartnerDetailsResponse updateDetails(Long pid, IndustryPartnerDetailsRequest request);

    void deleteDetails(Long pid);
}