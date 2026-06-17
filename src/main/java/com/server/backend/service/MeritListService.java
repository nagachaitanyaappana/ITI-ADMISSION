
package com.server.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.entity.MeritList;
import com.server.backend.Repository.MeritListRepository;

@Service
public class MeritListService {

<<<<<<< HEAD
=======

>>>>>>> 52ebe2f9e12e4c33d54e06c0d1f5996102fc0df9
    private MeritListRepository meritListRepository;

    public List<MeritList> getAllMeritList() {
        return meritListRepository.findAll();
    }
}