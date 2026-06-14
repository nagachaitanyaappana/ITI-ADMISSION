package com.server.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.backend.entity.Checklist;
import com.server.backend.Repository.ChecklistRepository;

@Service
public class ChecklistService {

    @Autowired
    private ChecklistRepository checklistRepository;

    public List<Checklist> getAllChecklist() {
        return checklistRepository.findAll();
    }

    public Checklist getChecklistByRegId(String regId) {
        return checklistRepository.findById(regId).orElse(null);
    }

    public List<Checklist> getChecklistByDistCode(String distCode) {
        return checklistRepository.findByDistCode(distCode);
    }

    public List<Checklist> getChecklistByPhase(String phase) {
        return checklistRepository.findByPhase(phase);
    }

    public List<Checklist> getChecklistByItiCode(String itiCode) {
        return checklistRepository.findByItiCode(itiCode);
    }

    public List<Checklist> getChecklistByAppStatus(String appStatus) {
        return checklistRepository.findByAppStatus(appStatus);
    }
}