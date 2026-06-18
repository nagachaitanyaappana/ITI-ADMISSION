package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.RankEntity;
import com.server.backend.entity.RankId;

public interface RankRepository extends JpaRepository<RankEntity, RankId> {

    List<RankEntity> findByRankAndPhaseAndYear(
            String rank,
            String phase,
            String year);

}