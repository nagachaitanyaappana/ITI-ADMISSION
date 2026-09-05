package com.server.backend.Repository.PlacementsRepositories;

import com.server.backend.entity.Placements.IndustryPartnerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.server.backend.DTO.Implant.IndustryPartnerExcelRow;
public interface IndustryPartnerDetailsRepository
        extends JpaRepository<IndustryPartnerDetails, Long> {
                @Query(value = """
    SELECT
        d.dist_name,
        i.iti_name,
        i.iti_code,
        ip.revised_lead_sector,
        ip.proposed_new_trade,
        ip.revised_lead_industry_partner
    FROM implant.industry_partner_details ip
    LEFT JOIN public.dist_mst d
        ON ip.dist_code = d.dist_code
    LEFT JOIN public.iti i
        ON ip.iti_code = i.iti_code
    ORDER BY ip.pid
    """, nativeQuery = true)
List<IndustryPartnerExcelRow> findIndustryPartnerExcelRows();
}