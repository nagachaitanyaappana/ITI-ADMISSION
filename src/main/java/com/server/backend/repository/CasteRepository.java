package com.server.backend.repository;

import com.server.backend.entity.CasteMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasteRepository extends JpaRepository<CasteMaster, Long> {

}