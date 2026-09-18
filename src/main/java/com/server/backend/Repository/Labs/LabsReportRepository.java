package com.server.backend.Repository.Labs;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.labs.Labs;

public interface LabsReportRepository extends JpaRepository<Labs, Long> {

    @Query(value = """
            SELECT
                l.lab_id,
                li.lab_items_id,
                l.iti_code,
                l.industry_name,
                l.trade_short,
                l.description,
                li.item_name,
                li.item_cost,
                li.item_photo
            FROM labs.labs l
            LEFT JOIN labs.labitems li
                ON l.lab_id = li.lab_id
            WHERE (:itiCode IS NULL OR l.iti_code = :itiCode)
              AND (:industryName IS NULL OR l.industry_name = :industryName)
            """, nativeQuery = true)
    List<Object[]> getLabsReport(
            @Param("itiCode") String itiCode,
            @Param("industryName") String industryName);

}
