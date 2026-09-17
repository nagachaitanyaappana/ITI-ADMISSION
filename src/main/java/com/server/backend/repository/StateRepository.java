package com.server.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.server.backend.entity.StateMst;

public interface StateRepository extends JpaRepository<StateMst, String> {

}