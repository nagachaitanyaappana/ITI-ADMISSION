package com.server.backend.service.Implant;

import com.server.backend.DTO.Implant.IndustryPartnerDetailsRequest;
import com.server.backend.DTO.Implant.IndustryPartnerDetailsResponse;
import com.server.backend.entity.Placements.IndustryPartnerDetails;
import com.server.backend.Repository.PlacementsRepositories.IndustryPartnerDetailsRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndustryPartnerDetailsServiceImpl
        implements IndustryPartnerDetailsService {

    private final IndustryPartnerDetailsRepository repository;
    private final JdbcTemplate jdbcTemplate;

    public IndustryPartnerDetailsServiceImpl(
            IndustryPartnerDetailsRepository repository,
            JdbcTemplate jdbcTemplate) {

        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // CREATE
    @Override
    @Transactional
    public IndustryPartnerDetailsResponse createDetails(
            IndustryPartnerDetailsRequest request) {

        validateRequest(request);

        IndustryPartnerDetails entity =
                new IndustryPartnerDetails();

        entity.setDistCode(request.getDistCode());
        entity.setItiCode(request.getItiCode());
        entity.setRevisedLeadSector(
                request.getRevisedLeadSector()
        );
        entity.setProposedNewTrade(
                request.getProposedNewTrade()
        );
        entity.setRevisedLeadIndustryPartner(
                request.getRevisedLeadIndustryPartner()
        );

        entity.setEntryDate(
                new Timestamp(System.currentTimeMillis())
        );

        entity.setEntryBy(request.getEntryBy());

        IndustryPartnerDetails saved =
                repository.save(entity);

        return convertToResponse(saved);
    }


    // GET ALL
    @Override
    @Transactional(readOnly = true)
    public List<IndustryPartnerDetailsResponse> getAllDetails() {

        return repository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // GET ONE
    @Override
    @Transactional(readOnly = true)
    public IndustryPartnerDetailsResponse getDetailsById(
            Long pid) {

        IndustryPartnerDetails entity =
                repository.findById(pid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Industry partner details not found: "
                                                + pid
                                )
                        );

        return convertToResponse(entity);
    }


    // UPDATE
    @Override
    @Transactional
    public IndustryPartnerDetailsResponse updateDetails(
            Long pid,
            IndustryPartnerDetailsRequest request) {

        validateRequest(request);

        IndustryPartnerDetails entity =
                repository.findById(pid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Industry partner details not found: "
                                                + pid
                                )
                        );

        entity.setDistCode(request.getDistCode());
        entity.setItiCode(request.getItiCode());
        entity.setRevisedLeadSector(
                request.getRevisedLeadSector()
        );
        entity.setProposedNewTrade(
                request.getProposedNewTrade()
        );
        entity.setRevisedLeadIndustryPartner(
                request.getRevisedLeadIndustryPartner()
        );

        IndustryPartnerDetails updated =
                repository.save(entity);

        return convertToResponse(updated);
    }


    // DELETE
    @Override
    @Transactional
    public void deleteDetails(Long pid) {

        IndustryPartnerDetails entity =
                repository.findById(pid)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Industry partner details not found: "
                                                + pid
                                )
                        );

        repository.delete(entity);
    }


    // Convert Entity → Response
    private IndustryPartnerDetailsResponse convertToResponse(
            IndustryPartnerDetails entity) {

        IndustryPartnerDetailsResponse response =
                new IndustryPartnerDetailsResponse();

        response.setPid(entity.getPid());
        response.setDistCode(entity.getDistCode());
        response.setItiCode(entity.getItiCode());
        response.setRevisedLeadSector(entity.getRevisedLeadSector());
        response.setProposedNewTrade(entity.getProposedNewTrade());
        response.setRevisedLeadIndustryPartner(entity.getRevisedLeadIndustryPartner());
        response.setEntryBy(entity.getEntryBy());
        response.setEntryDate(entity.getEntryDate());

        try {
            List<Map<String, Object>> dist = jdbcTemplate.queryForList(
                    "SELECT dist_name FROM public2.dist_mst WHERE dist_code = ?",
                    entity.getDistCode());
            if (!dist.isEmpty()) {
                response.setDistName((String) dist.get(0).get("dist_name"));
            }
        } catch (Exception ignored) { /* leave blank */ }

        try {
            List<Map<String, Object>> iti = jdbcTemplate.queryForList(
                    "SELECT iti_name FROM public2.iti WHERE iti_code = ?",
                    entity.getItiCode());
            if (!iti.isEmpty()) {
                response.setItiName((String) iti.get(0).get("iti_name"));
            }
        } catch (Exception ignored) { /* leave blank */ }

        return response;
    }


    // Validation
    private void validateRequest(
            IndustryPartnerDetailsRequest request) {

        if (request.getDistCode() == null ||
                request.getDistCode().isBlank()) {

            throw new RuntimeException(
                    "District code is required"
            );
        }

        if (request.getItiCode() == null ||
                request.getItiCode().isBlank()) {

            throw new RuntimeException(
                    "ITI code is required"
            );
        }
    }
}