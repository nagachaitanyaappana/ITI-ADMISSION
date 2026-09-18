package com.server.backend.controller.Student;


import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.student.CasteSubCasteResponseDto;
import com.server.backend.service.Student.CasteService;

@Tag(name = "Student")
@RestController
@RequestMapping("/api/student")
public class CasteController {

    private final CasteService casteService;

    // Constructor Injection only
    public CasteController(CasteService casteService) {
        this.casteService = casteService;
    }

    @GetMapping("/caste-subcaste")
    public List<CasteSubCasteResponseDto> getCasteSubCaste() {
        return casteService.getAllCasteWithSubCaste();
    }
}