package com.server.backend.controller.Admission;

    
    import java.time.LocalDate;
    import java.util.List;
    
    import org.springframework.stereotype.Service;
    
    import com.server.backend.DTO.Admission_Search_DTO;
    import com.server.backend.Repository.Admission.Admission_Search_Repository;
    import com.server.backend.entity.iti_admissions;
    
    @Service
    public class Admission_Search_ServiceImpl
            implements Admission_Search_Service {
    
        private final Admission_Search_Repository repository;
    
        public Admission_Search_ServiceImpl(
                Admission_Search_Repository repository) {
    
            this.repository = repository;
        }
    
        @Override
        public List<Admission_Search_DTO> searchAdmissions(
    
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
                Boolean includeExamsTable) {
    
            List<iti_admissions> admissions =
                    repository.searchAdmissions(
    
                            admissionNumber,
                            registrationNumber,
                            name,
                            fatherName,
                            phoneNumber,
                            gender,
                            caste,
                            reservationCategory,
                            yearOfAdmission,
                            dateOfAdmission,
                            typeOfAdmission,
                            currentSem,
                            districtCode,
                            itiCode,
                            tradeCode,
                            phase);
    
            return admissions.stream()
                    .map(this::convertToDTO)
                    .toList();
        }
    
        private Admission_Search_DTO convertToDTO(
                iti_admissions admission) {
    
            return new Admission_Search_DTO(
    
                    admission.getAdm_num(),
                    admission.getRegno(),
                    admission.getName(),
                    admission.getFname(),
                    admission.getPhno(),
                    admission.getGender(),
                    admission.getCaste(),
                    admission.getRes_category(),
                    admission.getYear_of_admission(),
                    admission.getDate_of_admission(),
                    admission.getType_admission(),
                    admission.getCurrent_sem(),
                    admission.getDist_code(),
                    admission.getIti_code(),
                    admission.getTrade_code(),
                    admission.getPhase()
            );
        }
    
}

import com.server.backend.service.Admission.Admission_Search_Service;