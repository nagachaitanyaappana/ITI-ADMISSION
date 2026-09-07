package com.server.backend.service.Implant;

import com.server.backend.DTO.Implant.IndustryMasterRequest;
import com.server.backend.DTO.Implant.IndustryMasterResponse;
import com.server.backend.entity.Placements.IndustryMaster;
import com.server.backend.Repository.PlacementsRepositories.IndustryMasterRepository;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
@Service
public class IndustryMasterServiceImpl implements IndustryMasterService {

    private final IndustryMasterRepository repository;

    public IndustryMasterServiceImpl(
            IndustryMasterRepository repository) {
        this.repository = repository;
    }

    @Override
    public IndustryMasterResponse createIndustry(
            IndustryMasterRequest request) {

        if (request.getIndustryName() == null || request.getIndustryName().trim().isEmpty()) {
            throw new IllegalArgumentException("Industry Name is required.");
        }
        if (request.getIndustryType() == null
                || (!request.getIndustryType().equals("Major") && !request.getIndustryType().equals("Minor"))) {
            throw new IllegalArgumentException("Industry Type must be Major or Minor.");
        }

        IndustryMaster entity = new IndustryMaster();

        entity.setIndustryName(request.getIndustryName());
        entity.setIndustryType(request.getIndustryType());
        entity.setIndustryAddress(request.getIndustryAddress());

        entity.setEntryBy(request.getEntryBy());
        entity.setEntryTime(new Timestamp(System.currentTimeMillis()));
        IndustryMaster savedEntity =
                repository.save(entity);

        return new IndustryMasterResponse(
                savedEntity.getIndustryId(),
                savedEntity.getIndustryName(),
                savedEntity.getIndustryType(),
                savedEntity.getIndustryAddress()
        );
    }
    @Override
public List<IndustryMasterResponse> getAllIndustries() {

    return repository.findAll()
            .stream()
            .map(industry -> new IndustryMasterResponse(
                    industry.getIndustryId(),
                    industry.getIndustryName(),
                    industry.getIndustryType(),
                    industry.getIndustryAddress()
            ))
            .toList();
}
@Override
public IndustryMasterResponse getIndustryById(Long industryId) {

    IndustryMaster industry = repository.findById(industryId)
            .orElseThrow(() ->
                    new RuntimeException("Industry not found with ID: " + industryId));

    return new IndustryMasterResponse(
            industry.getIndustryId(),
            industry.getIndustryName(),
            industry.getIndustryType(),
            industry.getIndustryAddress()
    );
}
@Override
public IndustryMasterResponse updateIndustry(
        Long industryId,
        IndustryMasterRequest request) {

    IndustryMaster industry = repository.findById(industryId)
            .orElseThrow(() ->
                    new RuntimeException("Industry not found with ID: " + industryId));

    if (request.getIndustryName() == null || request.getIndustryName().trim().isEmpty()) {
        throw new IllegalArgumentException("Industry Name is required.");
    }
    if (request.getIndustryType() == null
            || (!request.getIndustryType().equals("Major") && !request.getIndustryType().equals("Minor"))) {
        throw new IllegalArgumentException("Industry Type must be Major or Minor.");
    }

    industry.setIndustryName(request.getIndustryName());
    industry.setIndustryType(request.getIndustryType());
    industry.setIndustryAddress(request.getIndustryAddress());

    industry.setEditTime(new Timestamp(System.currentTimeMillis()));

    IndustryMaster updatedIndustry = repository.save(industry);

    return new IndustryMasterResponse(
            updatedIndustry.getIndustryId(),
            updatedIndustry.getIndustryName(),
            updatedIndustry.getIndustryType(),
            updatedIndustry.getIndustryAddress()
    );
}
@Override
public void deleteIndustry(Long industryId) {

    IndustryMaster industry = repository.findById(industryId)
            .orElseThrow(() ->
                    new RuntimeException("Industry not found with ID: " + industryId));

    repository.delete(industry);
}
}