package com.server.backend.service.Admission;
    

import java.util.List;

import com.server.backend.DTO.Call_Letter_DTO;

public interface Call_Letter_Service {

    List<Call_Letter_DTO> getCallLetterData(
            String rank,
            String caste);

}
