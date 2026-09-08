package com.server.backend.Repository.PlacementsRepositories;
import com.server.backend.entity.Placements.ImplantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface ImplantRepository extends JpaRepository<ImplantEntity, Long> {
    List<ImplantEntity> findByItiCode(String itiCode);
     
  @Query(value = """
    SELECT
        i.implant_id,
        it.iti_name,
        ind.industry_name,
        i.faculty_name,
        ind.trade_name,
        i.industry_address,
        i.hr_no,
        i.from_date,
        i.to_date,
        i.no_of_days,
        i.no_of_students,
        sm.statename,
        dm.dist_name,
        i.location,
        i.description
    FROM implant.implant i
    JOIN implant.industries ind
        ON CAST(i.iti_code AS INTEGER) = ind.iti_code
       AND i.trade_short = ind.trade_short
    LEFT JOIN iti it
        ON i.iti_code = it.iti_code
    LEFT JOIN dist_mst dm
        ON it.dist_code = dm.dist_code
    LEFT JOIN states_mast sm
        ON dm.statecode = sm.statecode
    WHERE i.iti_code = :itiCode
    ORDER BY i.implant_id
    """, nativeQuery = true)
List<Object[]> getReportData(@Param("itiCode") String itiCode);    


}



