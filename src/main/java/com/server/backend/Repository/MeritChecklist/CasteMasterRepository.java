package com.server.backend.Repository.MeritChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.backend.entity.CasteMasterPublic;
public interface CasteMasterRepository extends JpaRepository<CasteMasterPublic,String>{
    
}
