package com.server.backend.service.Student;

import com.server.backend.DTO.StudentApplicationDto;
import java.util.List;
import com.server.backend.entity.CasteMasterPublic;
import com.server.backend.entity.SubCasteMaster;
public interface StudentApplicationService {

    StudentApplicationDto saveStudent(StudentApplicationDto dto);

    StudentApplicationDto updateStudent(Integer regid, StudentApplicationDto dto);

    StudentApplicationDto getStudentById(Integer regid);

    StudentApplicationDto getStudentByHallTicket(String sscRegNo);

    List<StudentApplicationDto> getAllStudents();

    void deleteStudent(Integer regid);
    List<CasteMasterPublic> getAllCastes();

List<SubCasteMaster> getSubCastes(Long casteId);
}
