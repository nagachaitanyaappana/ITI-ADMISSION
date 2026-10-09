package com.server.backend.service.Student;

import com.server.backend.DTO.StudentCandMarksDto;
import com.server.backend.entity.StudentCandMarks;
import com.server.backend.Repository.Student.StudentApplicationRepository;
import com.server.backend.Repository.Student.StudentCandMarksRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StudentCandMarksServiceImpl implements StudentCandMarksService {

    private final StudentCandMarksRepository marksRepository;
    private final StudentApplicationRepository applicationRepository;

    public StudentCandMarksServiceImpl(StudentCandMarksRepository marksRepository,
            StudentApplicationRepository applicationRepository) {
        this.marksRepository = marksRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public String saveMarks(StudentCandMarksDto dto) {

        // Check whether regid exists in student_application
        applicationRepository.findById(dto.getRegid())
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        String regid = String.valueOf(dto.getRegid());

        // Re-save must overwrite (UPDATE), never create a second row.
        // Without this check every retry / double-click inserted a duplicate.
        StudentCandMarks entity = marksRepository.findById(regid)
                .orElseGet(StudentCandMarks::new);

        BeanUtils.copyProperties(dto, entity, "regid");
        entity.setRegid(regid);

        entity.setEntryDate(LocalDateTime.now());

        marksRepository.save(entity);

        return "Marks Saved Successfully";
    }
}