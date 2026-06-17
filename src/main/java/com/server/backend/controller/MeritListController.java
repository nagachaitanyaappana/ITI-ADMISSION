package com.server.backend.controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.server.backend.entity.MeritList;
import com.server.backend.service.MeritListService;

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
}