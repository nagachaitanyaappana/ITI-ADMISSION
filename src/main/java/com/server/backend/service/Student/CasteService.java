
package com.server.backend.service.Student;

import java.util.List;
import com.server.backend.DTO.CasteSubCasteResponseDto;

public interface CasteService {

    List<CasteSubCasteResponseDto> getAllCasteWithSubCaste();
}