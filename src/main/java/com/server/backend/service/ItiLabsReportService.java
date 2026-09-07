package com.server.backend.service;
import java.util.List;

import com.server.backend.DTO.LabsReportDTO;

public interface ItiLabsReportService {

    List<LabsReportDTO> getItiLabsReport(String itiCode);

    
}
