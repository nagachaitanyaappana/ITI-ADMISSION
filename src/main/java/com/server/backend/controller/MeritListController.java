package com.server.backend.controller;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.server.backend.entity.MeritList;
import com.server.backend.service.MeritListService;
import com.server.backend.entity.MeritListId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
@RequestMapping("/api/meritlist")
public class MeritListController {


    private final MeritListService meritListService;



    public MeritListController(MeritListService meritListService) {
        this.meritListService = meritListService;
    }
    @GetMapping
    public List<MeritList> getAllMeritList() {
        return meritListService.getAllMeritList();
    }
    @GetMapping("/{regid}")
    public MeritList getMeritListByRegId(@PathVariable Integer regid){
        return meritListService.getMeritListByRegId(regid);
    }

    @GetMapping("/district/{dist_code}")
    public List<MeritList> getMeritListByDistCode(
            @PathVariable String dist_code){
        return meritListService.getMeritListByDistCode(dist_code);
    }

    @GetMapping("/phase/{phase}")
    public List<MeritList> getMeritListByPhase(
            @PathVariable String phase) {
        return meritListService.getMeritListByPhase(phase);
    }

    @GetMapping("/iti/{iti_code}")
    public List<MeritList> getMeritListByItiCode(
            @PathVariable String iti_code){
        return meritListService.getMeritListByItiCode(iti_code);
    }

    @GetMapping("/status/{app_status}")
    public List<MeritList> getMeritListByAppStatus(
            @PathVariable String app_status){
        return meritListService.getMeritListByAppStatus(app_status);
    }
@PostMapping
public MeritList createMeritList(@RequestBody MeritList meritList) {
    
    return meritListService.saveMeritList(meritList);
}
@PutMapping("{regid}")
public MeritList updateMeritList(@RequestBody MeritList meritList) {

    
    return meritListService.updateMeritList(meritList);
}
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

