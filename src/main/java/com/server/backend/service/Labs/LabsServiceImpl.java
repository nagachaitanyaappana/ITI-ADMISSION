package com.server.backend.service.Labs;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.LabsDashboardResponse;

@Service
public class LabsServiceImpl implements LabsService {

    private final JdbcTemplate jdbcTemplate;

    public LabsServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public LabsDashboardResponse getLabsDashboardDetails() {
        LabsDashboardResponse response = new LabsDashboardResponse();

        response.setLabsTotal(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM labs.labs", Long.class));
        response.setLabsDistinctItis(jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT iti_code) FROM labs.labs", Long.class));
        response.setLabsDistinctTrades(jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT trade_short) FROM labs.labs", Long.class));
        response.setLabItemsTotal(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM labs.labitems", Long.class));
        response.setLabItemsDistinctItems(jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT item_name) FROM labs.labitems", Long.class));

        return response;
    }
}
