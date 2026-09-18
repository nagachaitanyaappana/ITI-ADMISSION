package com.server.backend.service.Student;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.backend.DTO.CasteSubCasteResponseDto;
import com.server.backend.DTO.SubCasteDto;
import com.server.backend.entity.CasteMaster;
import com.server.backend.entity.SubCasteMaster;
import com.server.backend.Repository.Student.CasteMaster_Repo;
import com.server.backend.Repository.Student.SubCasteMaster_Repo;
//import com.server.backend.service.Student.CasteService;

@Service
public class CasteServiceImpl implements CasteService {

    private final CasteMaster_Repo casteMasterRepository;
    private final SubCasteMaster_Repo subCasteMasterRepository;

    // ✅ Constructor Injection (NO @Autowired)
    public CasteServiceImpl(CasteMaster_Repo casteMasterRepository,
                            SubCasteMaster_Repo subCasteMasterRepository) {
        this.casteMasterRepository = casteMasterRepository;
        this.subCasteMasterRepository = subCasteMasterRepository;
    }
    @Override
    public List<CasteSubCasteResponseDto> getAllCasteWithSubCaste() {

        List<CasteMaster> castes = casteMasterRepository.findAll();

        return castes.stream().map(caste -> {

            List<SubCasteMaster> subCastes =
                    subCasteMasterRepository.findByCasteMaster(caste);

            List<SubCasteDto> subCasteList = subCastes.stream()
                    .map(sc -> new SubCasteDto(
                            sc.getSubcasteId(),
                            sc.getSubCaste()
                    ))
                    .collect(Collectors.toList());

            return new CasteSubCasteResponseDto(
                    caste.getCasteId(),
                    caste.getCasteCategory(),
                    subCasteList
            );

        }).collect(Collectors.toList());
    }
}