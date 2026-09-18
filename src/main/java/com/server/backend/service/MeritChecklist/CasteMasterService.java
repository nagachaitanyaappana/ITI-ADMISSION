package com.server.backend.service.MeritChecklist;
import com.server.backend.Repository.MeritChecklist.CasteMasterRepository;
import com.server.backend.entity.CasteMasterPublic;
import java.util.List;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
@Service
public class CasteMasterService {
    private final CasteMasterRepository casteMasterRepository;

    public CasteMasterService(CasteMasterRepository casteMasterRepository) {
        this.casteMasterRepository = casteMasterRepository;
    }
    public List<CasteMasterPublic> getAllCasteMasters() {
        List<CasteMasterPublic> getAllCasteMasters = casteMasterRepository.findAll();
        LinkedHashMap<String, CasteMasterPublic> response = new LinkedHashMap<>();
        for(CasteMasterPublic caste:getAllCasteMasters){
            response.putIfAbsent(caste.getCasteCode(),caste);
        }
        return new ArrayList<CasteMasterPublic>(response.values());
    }
}
