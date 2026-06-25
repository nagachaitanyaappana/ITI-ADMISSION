package com.server.backend.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.entity.MeritList;
import com.server.backend.entity.MeritListId;
import com.server.backend.service.MeritListService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "merit-list", description = "Merit list management operations")
@RestController
@RequestMapping("/api/meritlist")
public class MeritListController {


    private final MeritListService meritListService;



    public MeritListController(MeritListService meritListService) {
        this.meritListService = meritListService;
    }
    @Operation(summary = "Get all merit lists")
    @GetMapping
    public List<MeritList> getAllMeritList() {
        return meritListService.getAllMeritList();
    }
    @Operation(summary = "Get merit list by registration ID")
    @GetMapping("/{regid}")
    public MeritList getMeritListByRegId(@PathVariable Integer regid){
        return meritListService.getMeritListByRegId(regid);
    }

    @Operation(summary = "Get merit list by district code")
    @GetMapping("/district/{dist_code}")
    public List<MeritList> getMeritListByDistCode(
            @PathVariable String dist_code){
        return meritListService.getMeritListByDistCode(dist_code);
    }

    @Operation(summary = "Get merit list by phase")
    @GetMapping("/phase/{phase}")
    public List<MeritList> getMeritListByPhase(
            @PathVariable String phase) {
        return meritListService.getMeritListByPhase(phase);
    }

    @Operation(summary = "Get merit list by ITI code")
    @GetMapping("/iti/{iti_code}")
    public List<MeritList> getMeritListByItiCode(
            @PathVariable String iti_code){
        return meritListService.getMeritListByItiCode(iti_code);
    }

    @Operation(summary = "Get merit list by application status")
    @GetMapping("/status/{app_status}")
    public List<MeritList> getMeritListByAppStatus(
            @PathVariable String app_status){
                if("null".equalsIgnoreCase(app_status)) {
                    return meritListService.getMeritListByAppStatusIsNull();
                }
        return meritListService.getMeritListByAppStatus(app_status);
    }
    @Operation(summary = "Create a new merit list")
    @PostMapping
public MeritList createMeritList(@RequestBody MeritList meritList) {
    
    return meritListService.saveMeritList(meritList);
}
@Operation(summary = "Update an existing merit list")
@PutMapping("{regid}")
public MeritList updateMeritList(@RequestBody MeritList meritList) {

    
    return meritListService.updateMeritList(meritList);
}
@Operation(summary = "Delete a merit list")
@DeleteMapping("{regid}")
public void deleteMeritList(
        @RequestParam Integer regid,
        @RequestParam String qual,
        @RequestParam String temp_pk,
        @RequestParam String phase)
        {
            MeritListId meritListId = new MeritListId(regid, qual, temp_pk, phase);
            meritListService.deleteMeritList(meritListId);
        }

}

