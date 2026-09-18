package com.server.backend.Repository.ITI;

import org.springframework.data.jpa.repository.JpaRepository;


import com.server.backend.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, String> {

}