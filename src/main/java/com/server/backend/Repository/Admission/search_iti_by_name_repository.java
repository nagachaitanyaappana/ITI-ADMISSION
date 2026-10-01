package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti;

public interface search_iti_by_name_repository extends JpaRepository<Iti, String> {

    @Query(value = "SELECT * FROM iti WHERE LOWER(iti_name) LIKE LOWER(CONCAT('%', :itiName, '%'))", nativeQuery = true)
    List<Iti> searchItiByName(@Param("itiName") String itiName);

}