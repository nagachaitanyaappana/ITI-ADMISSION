package com.server.backend.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.entity.Checklist;
import com.server.backend.service.ChecklistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "checklist", description = "Checklist management operations")
@RestController
@RequestMapping("/api/checklist")
public class ChecklistController {


    private final ChecklistService checklistService;

    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @Operation(summary = "Get all checklists")
    @GetMapping
    public List<Checklist> getAllChecklist() {
        return checklistService.getAllChecklist();
    }
@Operation(summary = "Get checklist by registration ID")
    @GetMapping("/{regid}")
    public Checklist getChecklistByRegId(@PathVariable Integer regid){
        return checklistService.getChecklistByRegId(regid);
    }
@Operation(summary = "Get checklist by district code")
    @GetMapping("/district/{dist_code}")
    public List<Checklist> getChecklistByDistCode(
            @PathVariable String dist_code){
        return checklistService.getChecklistByDistCode(dist_code);
    }

    @Operation(summary = "Get checklist by phase")
    @GetMapping("/phase/{phase}")
    public List<Checklist> getChecklistByPhase(
            @PathVariable String phase) {
        return checklistService.getChecklistByPhase(phase);
    }

    @Operation(summary = "Get checklist by ITI code")
    @GetMapping("/iti/{iti_code}")
    public List<Checklist> getChecklistByItiCode(
            @PathVariable String iti_code){
        return checklistService.getChecklistByItiCode(iti_code);
    }

    @Operation(summary = "Get checklist by application status")
    @GetMapping("/status/{app_status}")
    public List<Checklist> getChecklistByAppStatus(
            @PathVariable String app_status){
                if("null".equalsIgnoreCase(app_status)) {
                    return checklistService.getChecklistByAppStatusIsNull();
                }
        return checklistService.getChecklistByAppStatus(app_status);
    }
     
    @Operation(summary = "Create a new checklist")
    @PostMapping
    public ResponseEntity<Checklist> createChecklist(
            @RequestBody Checklist checklist) {

        Checklist savedChecklist = checklistService.createChecklist(checklist);
        return ResponseEntity.ok(savedChecklist);
    }

    @Operation(summary = "Update an existing checklist")
    @PutMapping("/{regid}")
    public ResponseEntity<Checklist> updateChecklist(
            @PathVariable Integer regid,
            @RequestBody Checklist checklist) {

        Checklist updatedChecklist =
                checklistService.updateChecklist(regid, checklist);

        return ResponseEntity.ok(updatedChecklist);
    }

    @Operation(summary = "Delete a checklist")
    @DeleteMapping("/{regid}")
    public ResponseEntity<String> deleteChecklist(
            @PathVariable Integer regid) {

        checklistService.deleteChecklist(regid);
        return ResponseEntity.ok("Checklist deleted successfully");
    }
}
