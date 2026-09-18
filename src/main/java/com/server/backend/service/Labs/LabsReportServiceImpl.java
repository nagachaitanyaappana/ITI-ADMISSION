package com.server.backend.service.Labs;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.Repository.Labs.LabsReportRepository;
//import com.server.backend.service.Labs.LabsReportService;
import com.server.backend.DTO.LabsReportDTO;

@Service
public class LabsReportServiceImpl implements LabsReportService {

    private final LabsReportRepository labsReportRepository;

    public LabsReportServiceImpl(
            LabsReportRepository labsReportRepository) {
        this.labsReportRepository = labsReportRepository;
    }

    @Override
    public List<LabsReportDTO> getLabsReport(
            String itiCode,
            String industryName) {

        List<Object[]> results =
                labsReportRepository.getLabsReport(
                        itiCode,
                        industryName);

        List<LabsReportDTO> response = new ArrayList<>();

        for (Object[] row : results) {

            LabsReportDTO dto = new LabsReportDTO();

            dto.setLabId(
                    row[0] != null
                            ? ((Number) row[0]).longValue()
                            : null);

            dto.setLabItemsId(
                    row[1] != null
                            ? ((Number) row[1]).longValue()
                            : null);

            dto.setItiCode(
                    row[2] != null
                            ? row[2].toString()
                            : null);

            dto.setIndustryName(
                    row[3] != null
                            ? row[3].toString()
                            : null);

            dto.setTradeShort(
                    row[4] != null
                            ? row[4].toString()
                            : null);

            dto.setDescription(
                    row[5] != null
                            ? row[5].toString()
                            : null);

            dto.setItemName(
                    row[6] != null
                            ? row[6].toString()
                            : null);

            dto.setItemCost(
                    row[7] != null
                            ? ((Number) row[7]).doubleValue()
                            : null);

            dto.setItemPhoto(
                    row[8] != null
                            ? (byte[]) row[8]
                            : null);

            response.add(dto);
        }

        return response;
    }

}
