package com.server.backend.service.Student;

import com.server.backend.dto.StudentCandMarksDto;

public interface StudentCandMarksService {

    String saveMarks(StudentCandMarksDto dto);

}