package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti;

public interface itinames_by_govt_pvt_repository extends JpaRepository<Iti, String> {

    @Query(value = "SELECT iti_name FROM iti WHERE govt = :govt", nativeQuery = true)
    List<String> getItiNamesByGovtPvt(@Param("govt") String govt);

}