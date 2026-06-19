package com.server.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.entity.Checklist;
import com.server.backend.Repository.ChecklistRepository;

@Service
public class ChecklistService {


    private final ChecklistRepository checklistRepository;

    public ChecklistService(ChecklistRepository checklistRepository) {
        this.checklistRepository = checklistRepository;
    }
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
     public List<Checklist> getChecklistByAppStatusIsNull() {
        return checklistRepository.findByAppStatusIsNull();
    }
    // CREATE
public Checklist createChecklist(Checklist checklist) {
    return checklistRepository.save(checklist);
}

// UPDATE
public Checklist updateChecklist(Integer regid, Checklist checklist) {

    Checklist existing = checklistRepository.findById(regid)
            .orElseThrow(() -> new RuntimeException("Checklist not found: " + regid));

    existing.setDistCode(checklist.getDistCode());
    existing.setPhase(checklist.getPhase());
    existing.setItiCode(checklist.getItiCode());
    existing.setAppStatus(checklist.getAppStatus());

    return checklistRepository.save(existing);
}

// DELETE
public void deleteChecklist(Integer regid) {
    checklistRepository.deleteById(regid);
}
}
