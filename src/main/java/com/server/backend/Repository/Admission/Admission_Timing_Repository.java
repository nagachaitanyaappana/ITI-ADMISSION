package com.server.backend.Repository.Admission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.AdmissionTiming;

public interface Admission_Timing_Repository
        extends JpaRepository<AdmissionTiming, Long> {

    List<AdmissionTiming> findByMinqulAndCasteAndPhaseAndYear(
            String minqul,
            String caste,
            String phase,
            String year);

    /**
     * Filtered lookup for DISTLOGIN Schedule Entry screen.
     * {@code "all"} (any case) acts as a wildcard for qualification/caste so the
     * Screen-1 "All" option returns every row instead of matching the literal
     * string "all" (which never exists in admission_timings and always came back
     * empty). Comparisons are case-insensitive because the UI sends "10th"/"inter"
     * while legacy rows may store "10TH"/"Inter".
     */
    @Query("SELECT a FROM AdmissionTiming a "
            + "WHERE (:minqul = 'all' OR LOWER(a.minqul) = LOWER(:minqul)) "
            + "AND (:caste = 'all' OR LOWER(a.caste) = LOWER(:caste)) "
            + "AND a.phase = :phase AND a.year = :year")
    List<AdmissionTiming> findFilteredScheduleEntries(
            @Param("minqul") String minqul,
            @Param("caste") String caste,
            @Param("phase") String phase,
            @Param("year") String year);
}