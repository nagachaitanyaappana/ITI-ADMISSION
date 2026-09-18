package com.server.backend.Repository.ITI;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.ShiftUnitPermitted;
import com.server.backend.entity.ShiftUnitPermittedId;

public interface ShiftUnitPermittedRepository
        extends JpaRepository<ShiftUnitPermitted, ShiftUnitPermittedId> {

    List<ShiftUnitPermitted> findByItiCodeAndTradeCode(
            String itiCode,
            String tradeCode);

    boolean existsByItiCodeAndTradeCodeAndShiftAllowedAndUnitAllowed(
            String itiCode,
            String tradeCode,
            String shiftAllowed,
            String unitAllowed);

    
    void deleteByItiCodeAndTradeCode(
            String itiCode,
            String tradeCode);
}
