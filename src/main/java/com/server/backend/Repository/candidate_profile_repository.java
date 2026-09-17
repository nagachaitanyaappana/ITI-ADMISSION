package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.RankEntity;
import com.server.backend.entity.RankId;

public interface candidate_profile_repository extends JpaRepository<RankEntity, RankId> {

    @Query(value = """
        SELECT
            regid,
            rank,
            dist_code,
            qual,
            app_status
        FROM ranks
        WHERE regid = :regId
        """, nativeQuery = true)
    List<Object[]> getCandidateProfile(@Param("regId") Integer regId);

}