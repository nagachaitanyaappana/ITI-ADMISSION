package com.server.backend.service.Admission;



import java.time.LocalDate;
import java.util.List;

import com.server.backend.DTO.Admission_Search_DTO;

public interface Admission_Search_Service {

    List<Admission_Search_DTO> searchAdmissions(

            String admissionNumber,
            String registrationNumber,
            String name,
            String fatherName,
            Long phoneNumber,
            String gender,
            String caste,
            String reservationCategory,
            String yearOfAdmission,
            LocalDate dateOfAdmission,
            String typeOfAdmission,
            String currentSem,
            String districtCode,
            String itiCode,
            Integer tradeCode,
            Integer phase,

            String soundex,
            String selectedTable,
            Boolean includeExamsTable);

}
