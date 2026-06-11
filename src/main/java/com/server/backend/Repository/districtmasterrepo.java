package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.backend.entity.dist_master;

@Repository
public interface districtmasterrepo extends JpaRepository<dist_master, Long> {

    @Query("SELECT e.distname FROM dist_master e")
    List<String> findAllNames();
}
