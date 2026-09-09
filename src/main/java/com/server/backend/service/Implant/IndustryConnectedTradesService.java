package com.server.backend.service.Implant;

import com.server.backend.DTO.Industries.IndustryConnectedTradesDTO;

import java.util.List;

public interface IndustryConnectedTradesService {

    List<IndustryConnectedTradesDTO> getReport();

    byte[] downloadExcel();
}