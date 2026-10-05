package com.server.backend.Repository.Admission;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.StudentApplication;

public interface Application_Reprint_Repository
        extends JpaRepository<StudentApplication, Integer> {

    Optional<StudentApplication> findByRegid(Long regid);
}