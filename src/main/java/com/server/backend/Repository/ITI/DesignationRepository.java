package com.server.backend.Repository.ITI;

import org.springframework.data.jpa.repository.JpaRepository;


import com.server.backend.entity.ITI.Designation;

public interface DesignationRepository extends JpaRepository<Designation, String> {

}