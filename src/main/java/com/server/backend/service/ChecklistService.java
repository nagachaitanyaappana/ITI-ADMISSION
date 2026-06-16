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

    public Checklist getChecklistByRegId(Integer regid) {
        return checklistRepository.findById(regid).orElse(null);
    }

    public List<Checklist> getChecklistByDistCode(String dist_code) {
        return checklistRepository.findByDistCode(dist_code);
    }

    public List<Checklist> getChecklistByPhase(String phase) {
        return checklistRepository.findByPhase(phase);
    }

    public List<Checklist> getChecklistByItiCode(String iti_code) {
        return checklistRepository.findByItiCode(iti_code);
    }

    public List<Checklist> getChecklistByAppStatus(String app_status) {
        return checklistRepository.findByAppStatus(app_status);
    }
}