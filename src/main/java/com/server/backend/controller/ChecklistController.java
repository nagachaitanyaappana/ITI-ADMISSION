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
    public Checklist getChecklistByRegId(@PathVariable String reg_id){
        return checklistService.getChecklistByRegId(reg_id);
    }

    @GetMapping("/district/{distCode}")
    public List<Checklist> getChecklistByDistCode(
            @PathVariable String dist_code){
        return checklistService.getChecklistByDistCode(dist_code);
    }

    @GetMapping("/phase/{phase}")
    public List<Checklist> getChecklistByPhase(
            @PathVariable String phase) {
        return checklistService.getChecklistByPhase(phase);
    }

    @GetMapping("/iti/{itiCode}")
    public List<Checklist> getChecklistByItiCode(
            @PathVariable String iti_code){
        return checklistService.getChecklistByItiCode(iti_code);
    }

    @GetMapping("/status/{appStatus}")
    public List<Checklist> getChecklistByAppStatus(
            @PathVariable String app_status){
        return checklistService.getChecklistByAppStatus(app_status);
    }
}