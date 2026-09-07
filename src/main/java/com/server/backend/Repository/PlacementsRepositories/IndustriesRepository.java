package com.server.backend.Repository.PlacementsRepositories;
import com.server.backend.entity.Placements.Industries;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndustriesRepository extends JpaRepository<Industries, Long> {

    @Query(value = """
        SELECT DISTINCT iti_code, iti_name
        FROM implant.industries
        ORDER BY iti_name
        """, nativeQuery = true)
    List<Object[]> getItis();

    @Query(value = """
        SELECT DISTINCT industry_id, industry_name
        FROM implant.industries
        WHERE iti_code = :itiCode
        ORDER BY industry_name
        """, nativeQuery = true)
    List<Object[]> getIndustries(@Param("itiCode") Integer itiCode);
      List<Industries> findByItiCode(Integer itiCode);
      Boolean existsByItiCodeAndIndustryIdAndTradeCode(Integer itiCode, Long industryId, Integer tradeCode);
      boolean existsByItiCodeAndIndustryIdAndTradeCodeAndSlnoNot( Integer itiCode, Long industryId, Integer tradeCode,  Long slno);
}  
