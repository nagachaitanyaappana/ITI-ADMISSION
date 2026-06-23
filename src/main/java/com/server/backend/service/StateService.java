package com.server.backend.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.server.backend.Repository.StateRepository;
import com.server.backend.entity.StateMst;

@Service
public class StateService {

    private final StateRepository repository;

    public StateService(StateRepository repository) {
        this.repository = repository;
    }

    public List<StateMst> getAllStates() {
        return repository.findAll();
    }
}