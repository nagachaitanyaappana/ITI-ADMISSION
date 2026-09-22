package com.server.backend.Repository.Student;

import com.server.backend.entity.SubCasteMasterPublic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCasteMasterPublicRepository extends JpaRepository<SubCasteMasterPublic, Long> {
    List<SubCasteMasterPublic> findByCasteCodeOrderBySubCasteIdAsc(String casteCode);
}
