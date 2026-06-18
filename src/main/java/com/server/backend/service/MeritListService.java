
package com.server.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.backend.entity.MeritList;
import com.server.backend.entity.MeritListId;
import com.server.backend.Repository.MeritListRepository;

@Service
public class MeritListService {


    private final MeritListRepository meritListRepository;

    public MeritListService(MeritListRepository meritListRepository) {
        this.meritListRepository = meritListRepository;
    }

    public List<MeritList> getAllMeritList() {
        return meritListRepository.findAll();
    }
    public MeritList saveMeritList(MeritList meritList) {
        return meritListRepository.save(meritList);
    }
    public MeritList updateMeritList(MeritList meritList) {
        return meritListRepository.save(meritList);
    }
    public void deleteMeritList(MeritListId id) {
        meritListRepository.deleteById(id);
    }
}