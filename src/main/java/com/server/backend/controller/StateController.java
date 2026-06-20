package com.server.backend.controller;

import java.util.List;


import org.springframework.web.bind.annotation.*;

import com.server.backend.entity.StateMst;
import com.server.backend.service.StateService;

@RestController
@RequestMapping("/api/master")
public class StateController {

    private final StateService service;

    public StateController(StateService service) {
        this.service = service;
    }

    @GetMapping("/states")
    public List<StateMst> getStates() {
        return service.getAllStates();
    }
}