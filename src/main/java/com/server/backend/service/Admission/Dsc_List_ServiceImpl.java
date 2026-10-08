package com.server.backend.service.Admission;



import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.Dsc_List_DTO;
import com.server.backend.Repository.Admission.Dsc_List_Repository;

@Service
public class Dsc_List_ServiceImpl implements Dsc_List_Service {

    private final Dsc_List_Repository repository;

    public Dsc_List_ServiceImpl(Dsc_List_Repository repository) {
        this.repository = repository;
    }

    @Override
    public List<Dsc_List_DTO> getDscList(
            String itiCode,
            Integer tradeCode,
            Integer phase,
            String year,
            String admissionLevel) {

        List<Object[]> results = repository.findDscList(
                itiCode,
                tradeCode,
                phase,
                year);

        List<Dsc_List_DTO> response = new ArrayList<>();

        int slNo = 1;

        for (Object[] row : results) {

            String admissionNumber =
                    row[0] != null ? row[0].toString() : null;

            String name =
                    row[1] != null ? row[1].toString() : null;

            String fatherName =
                    row[2] != null ? row[2].toString() : null;

            String gender =
                    row[3] != null ? row[3].toString() : null;

            LocalDate dob = null;

            if (row[4] instanceof Date date) {
                dob = date.toLocalDate();
            }

            String caste =
                    row[5] != null ? row[5].toString() : null;

            response.add(
                    new Dsc_List_DTO(
                            slNo++,
                            null,
                            admissionNumber,
                            name,
                            fatherName,
                            gender,
                            dob,
                            caste
                    )
            );
        }

        return response;
    }

}
