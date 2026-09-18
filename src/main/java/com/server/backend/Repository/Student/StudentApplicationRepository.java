package com.server.backend.Repository.Student;

import com.server.backend.entity.StudentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentApplicationRepository extends JpaRepository<StudentApplication, Integer> {

    Optional<StudentApplication> findBySscRegNo(String sscRegNo);

}