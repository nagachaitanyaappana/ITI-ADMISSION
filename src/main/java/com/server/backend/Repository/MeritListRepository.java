package com.server.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.MeritList;
import com.server.backend.entity.MeritListId;

public interface MeritListRepository extends JpaRepository<MeritList, MeritListId> {
}s