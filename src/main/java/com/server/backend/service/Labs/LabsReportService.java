package com.server.backend.service.Labs;


import java.util.List;

import com.server.backend.DTO.LabsReportDTO;

public interface LabsReportService {

    List<LabsReportDTO> getLabsReport(
            String itiCode,
            String industryName);

}
