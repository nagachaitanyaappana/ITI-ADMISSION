package com.server.backend.Repository.MeritChecklist;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.server.backend.entity.RankEntity;
import com.server.backend.entity.RankId;

public interface MeritListRepository extends JpaRepository<RankEntity, RankId> {
    RankEntity findByRegid(Integer regid);
     List<RankEntity> findByDistCode(String dist_code);


    List<RankEntity> findByPhase(String phase);

    List<RankEntity> findByItiCode(String iti_code);

    List<RankEntity> findByAppStatus(String app_status);
    List<RankEntity> findByAppStatusIsNull();
}
