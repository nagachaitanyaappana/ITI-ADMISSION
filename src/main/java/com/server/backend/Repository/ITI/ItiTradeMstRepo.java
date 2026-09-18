

package com.server.backend.Repository.ITI;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.backend.entity.ITI.Iti_trade_mst;

public interface ItiTradeMstRepo
        extends JpaRepository<Iti_trade_mst, String> {

    /* Added for varshitha's admission module (trdnms_by_minqual_service) */
    List<Iti_trade_mst> findByMinQual(String minQual);

}