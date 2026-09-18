package com.server.backend.Repository.Student;

import com.server.backend.entity.StudentCandMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCandMarksRepository extends JpaRepository<StudentCandMarks, Integer> {

}