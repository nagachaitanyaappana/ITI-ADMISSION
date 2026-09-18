
package com.server.backend.service.Student;

import java.util.List;
import com.server.backend.DTO.student.CasteSubCasteResponseDto;

public interface CasteService {

    List<CasteSubCasteResponseDto> getAllCasteWithSubCaste();
}