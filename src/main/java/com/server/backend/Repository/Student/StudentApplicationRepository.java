package com.server.backend.Repository.Student;

import com.server.backend.entity.StudentApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentApplicationRepository extends JpaRepository<StudentApplication, Integer> {

    Optional<StudentApplication> findBySscRegNo(String sscRegNo);

}