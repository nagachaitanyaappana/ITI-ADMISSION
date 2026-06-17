package com.server.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.server.backend.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, String> {

}