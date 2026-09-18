package com.server.backend.service.Student;

import com.server.backend.DTO.Student.StudentCandMarksDto;
import com.server.backend.entity.StudentApplication;
import com.server.backend.entity.StudentCandMarks;
import com.server.backend.Repository.Student.StudentApplicationRepository;
import com.server.backend.Repository.Student.StudentCandMarksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudentCandMarksServiceImpl implements StudentCandMarksService {

    private final StudentCandMarksRepository marksRepository;
    private final StudentApplicationRepository applicationRepository;

    @Override
    public String saveMarks(StudentCandMarksDto dto) {

        // Check whether regid exists in student_application
        StudentApplication student = applicationRepository.findById(dto.getRegid())
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        StudentCandMarks entity = new StudentCandMarks();

        BeanUtils.copyProperties(dto, entity);
        entity.setRegid(String.valueOf(dto.getRegid()));

        entity.setEntryDate(LocalDateTime.now());

        marksRepository.save(entity);

        return "Marks Saved Successfully";
    }
}