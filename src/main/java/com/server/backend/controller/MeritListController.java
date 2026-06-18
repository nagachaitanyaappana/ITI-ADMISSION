package com.server.backend.controller;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@PostMapping
public MeritList createMeritList(@RequestBody MeritList meritList) {
    
    return meritListService.saveMeritList(meritList);
}
@PutMapping
public MeritList updateMeritList(@RequestBody MeritList meritList) {

    
    return meritListService.updateMeritList(meritList);
}
@DeleteMapping
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

