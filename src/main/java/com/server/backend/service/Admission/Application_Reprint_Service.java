package com.server.backend.service.Admission;


import com.server.backend.DTO.Application_Reprint_DTO;

public interface Application_Reprint_Service {

    Application_Reprint_DTO getApplication(Long registrationId);
}