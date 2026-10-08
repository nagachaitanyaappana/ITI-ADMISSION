package com.server.backend.service.Admission;



import java.util.List;

import com.server.backend.DTO.Dsc_List_DTO;

public interface Dsc_List_Service {

    List<Dsc_List_DTO> getDscList(
            String itiCode,
            Integer tradeCode,
            Integer phase,
            String year,
            String admissionLevel);

}
