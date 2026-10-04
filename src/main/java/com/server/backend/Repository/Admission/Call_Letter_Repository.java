package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.StudentApplication;

public interface Call_Letter_Repository
        extends JpaRepository<StudentApplication, Integer> {

    @Query(value = """
        SELECT
            r.rank AS rank,
            s.caste AS caste,
            r.regid AS regid,
            r.iti_code AS itiCode,
            r.qual AS qualification,
            r.temp_pk AS tempPk,
            r.phase AS phase,
            r.trno AS trno,
            r.year AS year,
            s.name AS name
        FROM public.ranks2025phase1 r
        INNER JOIN public.student_application s
            ON r.regid = s.regid
            AND r.year = s.year
            AND r.phase = s.phase
        WHERE r.rank = :rank
          AND s.caste = :caste
        """, nativeQuery = true)
    List<Object[]> findCallLetterData(
            @Param("rank") String rank,
            @Param("caste") String caste);
}

