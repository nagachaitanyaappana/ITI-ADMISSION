package com.server.backend.service.Student;

import com.server.backend.DTO.Student.StudentApplicationDto;
import com.server.backend.entity.StudentApplication;
import com.server.backend.Repository.Student.StudentApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.beans.PropertyDescriptor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.server.backend.entity.CasteMaster;
import com.server.backend.entity.SubCasteMaster;
import com.server.backend.Repository.Student.CasteRepository;
import com.server.backend.Repository.Student.SubCasteRepository;
@Service
@RequiredArgsConstructor
public class StudentApplicationServiceImpl implements StudentApplicationService {

    private final StudentApplicationRepository repository;
    private final CasteRepository casteRepository;

private final SubCasteRepository subCasteRepository;

    @Override
    public StudentApplicationDto saveStudent(StudentApplicationDto dto) {

        StudentApplication entity = new StudentApplication();

        // Copy request fields
        BeanUtils.copyProperties(dto, entity);

        // Default values
entity.setEntryDate(LocalDateTime.now());

entity.setAppStatus("N");     // 1 character only
entity.setDataFlag("A");      // 1 character only
entity.setPwdCategory("N");   // 1 character only

entity.setPhase("REGISTRATION");
entity.setSscPassed(true);

        StudentApplication saved = repository.save(entity);

        StudentApplicationDto response = new StudentApplicationDto();
        BeanUtils.copyProperties(saved, response);

        return response;
    }


    @Override
public StudentApplicationDto updateStudent(Integer regid, StudentApplicationDto dto) {

    StudentApplication entity = repository.findById(regid)
            .orElseThrow(() -> new RuntimeException("Student Not Found"));

    BeanWrapper src = new BeanWrapperImpl(dto);
    BeanWrapper trg = new BeanWrapperImpl(entity);

    for (PropertyDescriptor pd : src.getPropertyDescriptors()) {

        String propertyName = pd.getName();

        if ("class".equals(propertyName) || "regid".equals(propertyName)) {
            continue;
        }

        Object value = src.getPropertyValue(propertyName);

        if (value != null) {
            trg.setPropertyValue(propertyName, value);
        }
    }

    StudentApplication updated = repository.save(entity);

    StudentApplicationDto response = new StudentApplicationDto();
    BeanUtils.copyProperties(updated, response);

    return response;
}

    @Override
    public StudentApplicationDto getStudentById(Integer regid) {

        StudentApplication entity = repository.findById(regid)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        StudentApplicationDto dto = new StudentApplicationDto();
        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    @Override
    public StudentApplicationDto getStudentByHallTicket(String sscRegNo) {

        StudentApplication entity = repository.findBySscRegNo(sscRegNo)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        StudentApplicationDto dto = new StudentApplicationDto();
        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    @Override
    public List<StudentApplicationDto> getAllStudents() {

        return repository.findAll().stream().map(entity -> {
            StudentApplicationDto dto = new StudentApplicationDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Integer regid) {

        repository.deleteById(regid);

    }
    @Override
public List<CasteMaster> getAllCastes() {
    return casteRepository.findAll();
}

@Override
public List<SubCasteMaster> getSubCastes(Long casteId) {
    return subCasteRepository.findByCasteId(casteId);
}
}