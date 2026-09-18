package com.server.backend.Repository.MeritChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.backend.entity.AdmissionTiming;
import com.server.backend.entity.AdmissionTimingId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
public interface AdmissionTimingRepository extends JpaRepository<AdmissionTiming, AdmissionTimingId> {
    

// Add these native queries to find Year/Phase, Max ID, and Entity Names
@Query(value = "SELECT value FROM public.iti_params WHERE code = '7'", nativeQuery = true)
String findCurrentYearVal();

@Query(value = "SELECT phase FROM admissions.admission_phase WHERE year = :year AND current = 'true'", nativeQuery = true)
Optional<String> findCurrentPhaseVal(@Param("year") String year);

@Query(value = "SELECT phase, admissions_start_date, admissions_end_date FROM admissions.admission_phase WHERE year = :year AND current = 'true'", nativeQuery = true)
Optional<Object[]> findPhaseDates(@Param("year") String year);

@Query(value = "SELECT COALESCE(MAX(CAST(temp_pk AS integer)), 0) + 1 FROM public.admission_timings", nativeQuery = true)
Integer getNextTempPkVal();

@Query(value = "SELECT dist_name FROM public.dist_mst WHERE dist_code = :distCode", nativeQuery = true)
Optional<String> findDistName(@Param("distCode") String distCode);

@Query(value = "SELECT iti_name FROM public.iti WHERE iti_code = :itiCode", nativeQuery = true)
Optional<String> findItiName(@Param("itiCode") String itiCode);

// Add these to check for placeholder rows
Optional<AdmissionTiming> findFirstByItiCodeAndPhaseAndYearAndCasteAndMinqulAndMeritFrom(
    String itiCode, String phase, String year, String caste, String minqul, Integer meritFrom
);

Optional<AdmissionTiming> findFirstByDistCodeAndPhaseAndYearAndCasteAndMinqulAndMeritFrom(
    String distCode, String phase, String year, String caste, String minqul, Integer meritFrom
);

// Add these to check for Date/Time overlaps
boolean existsByItiCodeAndPhaseAndYearAndCalDateAndCalTimeAndTempPkNot(
    String itiCode, String phase, String year, LocalDate calDate, LocalTime calTime, String tempPk
);

boolean existsByDistCodeAndPhaseAndYearAndCalDateAndCalTimeAndTempPkNot(
    String distCode, String phase, String year, LocalDate calDate, LocalTime calTime, String tempPk
);

// Add these to check for Merit Range overlaps
@Query("SELECT t FROM AdmissionTiming t WHERE t.itiCode = :itiCode AND t.phase = :phase AND t.year = :year AND t.tempPk != :tempPk AND t.meritFrom != 0 AND (:meritFrom <= t.meritTo AND :meritTo >= t.meritFrom)")
List<AdmissionTiming> findOverlappingItiMeritRanges(
    @Param("itiCode") String itiCode, 
    @Param("phase") String phase, 
    @Param("year") String year, 
    @Param("tempPk") String tempPk, 
    @Param("meritFrom") Integer meritFrom, 
    @Param("meritTo") Integer meritTo
);

@Query("SELECT t FROM AdmissionTiming t WHERE t.distCode = :distCode AND t.phase = :phase AND t.year = :year AND t.tempPk != :tempPk AND t.meritFrom != 0 AND (:meritFrom <= t.meritTo AND :meritTo >= t.meritFrom)")
List<AdmissionTiming> findOverlappingDistMeritRanges(
    @Param("distCode") String distCode, 
    @Param("phase") String phase, 
    @Param("year") String year, 
    @Param("tempPk") String tempPk, 
    @Param("meritFrom") Integer meritFrom, 
    @Param("meritTo") Integer meritTo
);

// Add this to search for schedules dynamically in the View endpoint
@Query("SELECT t FROM AdmissionTiming t WHERE " +
       "((:useDist = true AND t.distCode = :code) OR (:useDist = false AND t.itiCode = :code)) " +
       "AND t.phase = :phase " +
       "AND t.year = :year " +
       "AND (:caste = 'all' OR t.caste = :caste) " +
       "AND (:minqul = 'all' OR t.minqul = :minqul) " +
       "AND (:calTime IS NULL OR t.calTime = :calTime) " +
       "ORDER BY t.calDate, t.calTime")
List<AdmissionTiming> findFilteredSchedules(
    @Param("useDist") boolean useDist,
    @Param("code") String code,
    @Param("phase") String phase,
    @Param("year") String year,
    @Param("caste") String caste,
    @Param("minqul") String minqul,
    @Param("calTime") LocalTime calTime
);
}