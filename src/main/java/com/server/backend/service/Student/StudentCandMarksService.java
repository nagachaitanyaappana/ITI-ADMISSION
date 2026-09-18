package com.server.backend.service.Student;

import com.server.backend.DTO.Student.StudentCandMarksDto;

public interface StudentCandMarksService {

    String saveMarks(StudentCandMarksDto dto);

}