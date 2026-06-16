package com.server.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.RankEntity;
import com.server.backend.entity.RankId;

public interface RankRepository extends JpaRepository<RankEntity, RankId> {

    Optional<RankEntity> findByRankAndPhaseAndYear(
            Integer rank,
            Integer phase,
            Integer year);

}