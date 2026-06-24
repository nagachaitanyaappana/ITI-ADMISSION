package com.server.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.entity.StateMst;
import com.server.backend.service.StateService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "states", description = "State management operations")
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