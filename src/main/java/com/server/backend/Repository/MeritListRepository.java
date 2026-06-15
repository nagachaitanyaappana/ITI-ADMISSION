package com.server.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.backend.entity.MeritList;
import com.server.backend.entity.MeritListId;

@Repository
public interface MeritListRepository extends JpaRepository<MeritList, MeritListId> {
}