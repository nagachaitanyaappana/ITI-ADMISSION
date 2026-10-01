package com.server.backend.service.Implant;

import com.server.backend.DTO.IndustryDropdownResponse;

import java.util.List;

public interface IndustriesService {

    List<IndustryDropdownResponse> getIndustriesByIti(Integer itiCode);
}