package com.server.backend.service.Student;

import java.util.List;


import org.springframework.stereotype.Service;

import com.server.backend.Repository.Student.StateRepository;
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