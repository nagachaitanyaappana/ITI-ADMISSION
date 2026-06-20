package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.server.backend.entity.Iti;


public interface trade_names_repository extends JpaRepository<Iti, String> {

    @Query(value = "SELECT iti_name FROM iti", nativeQuery = true)
    List<String> getAllItiNames();

    @Query(value = "SELECT trade_name FROM ititrade_master", nativeQuery = true)
    List<String> getAllTradeNames();

}
