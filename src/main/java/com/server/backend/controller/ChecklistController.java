package com.server.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.entity.Checklist;
import com.server.backend.service.ChecklistService;

@RestController
@RequestMapping("/api/checklist")
public class ChecklistController {

    @Autowired
    private ChecklistService checklistService;

    @GetMapping
    public List<Checklist> getAllChecklist() {
        return checklistService.getAllChecklist();
    }

    @GetMapping("/{regId}")
    public Checklist getChecklistByRegId(@PathVariable String regId) {
        return checklistService.getChecklistByRegId(regId);
    }

    @GetMapping("/district/{distCode}")
    public List<Checklist> getChecklistByDistCode(
            @PathVariable String distCode) {
        return checklistService.getChecklistByDistCode(distCode);
    }

    @GetMapping("/phase/{phase}")
    public List<Checklist> getChecklistByPhase(
            @PathVariable String phase) {
        return checklistService.getChecklistByPhase(phase);
    }

    @GetMapping("/iti/{itiCode}")
    public List<Checklist> getChecklistByItiCode(
            @PathVariable String itiCode) {
        return checklistService.getChecklistByItiCode(itiCode);
    }

    @GetMapping("/status/{appStatus}")
    public List<Checklist> getChecklistByAppStatus(
            @PathVariable String appStatus) {
        return checklistService.getChecklistByAppStatus(appStatus);
    }
}