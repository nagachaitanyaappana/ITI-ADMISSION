
package com.server.backend.service;

import java.util.List;
import com.server.backend.DTO.student.CasteSubCasteResponseDto;

public interface CasteService {

    List<CasteSubCasteResponseDto> getAllCasteWithSubCaste();
}