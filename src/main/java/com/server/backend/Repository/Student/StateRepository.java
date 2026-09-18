package com.server.backend.Repository.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import com.server.backend.entity.StateMst;

public interface StateRepository extends JpaRepository<StateMst, String> {

}