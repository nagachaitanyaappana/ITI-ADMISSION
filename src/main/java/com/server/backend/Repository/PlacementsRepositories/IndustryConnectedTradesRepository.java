package com.server.backend.Repository.PlacementsRepositories;

import com.server.backend.DTO.Industries.IndustryConnectedTradesDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IndustryConnectedTradesRepository {

    private final JdbcTemplate jdbcTemplate;

    public IndustryConnectedTradesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<IndustryConnectedTradesDTO> getReport() {

      String sql = """
SELECT
    i.dist_name,
    i.iti_code,
    i.iti_name,
    i.trade_name,
    SUM(COALESCE(im.no_of_students,0)) AS total_trainees,
    i.industry_name
FROM implant.industries i
LEFT JOIN implant.implant im
       ON i.iti_code = CAST(im.iti_code AS INTEGER)
      AND i.trade_short = im.trade_short
WHERE i.trade_short IS NOT NULL
GROUP BY
    i.dist_name,
    i.iti_code,
    i.iti_name,
    i.trade_name,
    i.industry_name
ORDER BY
    i.dist_name,
    i.iti_name
""";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            IndustryConnectedTradesDTO dto = new IndustryConnectedTradesDTO();

            dto.setSno((long)rowNum+1);
            dto.setDistrict(rs.getString("dist_name"));
            dto.setItiCode(rs.getInt("iti_code"));
            dto.setItiName(rs.getString("iti_name"));
            dto.setTradeName(rs.getString("trade_name"));
            dto.setTotalTrainees(rs.getInt("total_trainees"));
            dto.setIndustryName(rs.getString("industry_name"));

            return dto;
        });
    }
}