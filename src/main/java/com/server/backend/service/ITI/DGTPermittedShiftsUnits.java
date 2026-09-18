package com.server.backend.service.ITI;

import com.server.backend.DTO.ITI.ShiftUnitPermittedRequestDto;
import com.server.backend.DTO.ITI.ShiftUnitPermittedResponseDto;

public interface DGTPermittedShiftsUnits {

    ShiftUnitPermittedResponseDto save(ShiftUnitPermittedRequestDto dto);

    ShiftUnitPermittedResponseDto getByItiAndTrade(
            String itiCode,
            String tradeCode);

    void delete(
            String itiCode,
            String tradeCode,
            Integer shiftAllowed,
            Integer unitAllowed);
}