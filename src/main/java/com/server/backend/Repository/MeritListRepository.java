package com.server.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.server.backend.entity.MeritList;
import com.server.backend.entity.MeritListId;

public interface MeritListRepository extends JpaRepository<MeritList, MeritListId> {
    MeritList findByRegid(Integer regid);
     List<MeritList> findByDistCode(String dist_code);


    List<MeritList> findByPhase(String phase);

    List<MeritList> findByItiCode(String iti_code);

    List<MeritList> findByAppStatus(String app_status);
    List<MeritList> findByAppStatusIsNull();
}
