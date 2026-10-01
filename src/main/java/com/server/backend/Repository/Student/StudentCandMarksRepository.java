package com.server.backend.Repository.Student;
import com.server.backend.entity.StudentCandMarks;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentCandMarksRepository extends JpaRepository<StudentCandMarks, String> {

}