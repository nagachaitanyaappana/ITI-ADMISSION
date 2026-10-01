package com.server.backend.service.Labs;
import java.util.List;

import com.server.backend.DTO.LabsReportDTO;

public interface ItiLabsReportService {

    List<LabsReportDTO> getItiLabsReport(String itiCode);

    
}
