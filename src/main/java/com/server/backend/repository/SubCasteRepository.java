package com.server.backend.repository;

import com.server.backend.entity.SubCasteMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCasteRepository extends JpaRepository<SubCasteMaster, Long> {

    List<SubCasteMaster> findByCasteId(Long casteId);

}