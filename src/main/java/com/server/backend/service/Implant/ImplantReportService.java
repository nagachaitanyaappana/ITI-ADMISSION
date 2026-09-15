package com.server.backend.service.Implant;
import com.server.backend.DTO.Industries.ImplantIndustryResponse;
import com.server.backend.DTO.Industries.ImplantReportDTO;
import java.util.List;

public interface ImplantReportService {

    List<ImplantIndustryResponse> getIndustries(Integer itiCode);
    List<ImplantReportDTO> getImplantReportByIndustry(Integer industryId);

    byte[] downloadExcel(Integer industryId);
}