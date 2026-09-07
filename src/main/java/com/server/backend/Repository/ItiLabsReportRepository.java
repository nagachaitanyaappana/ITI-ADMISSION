package com.server.backend.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.labs.Labs;

public interface ItiLabsReportRepository extends JpaRepository<Labs, Long> {

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
            WHERE l.iti_code = :itiCode
            """, nativeQuery = true)
    List<Object[]> getItiLabsReport(
            @Param("itiCode") String itiCode);

    
}
