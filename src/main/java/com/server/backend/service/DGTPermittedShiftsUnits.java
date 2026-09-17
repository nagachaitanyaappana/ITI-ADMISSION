package com.server.backend.service;

import com.server.backend.DTO.Institute.ShiftUnitPermittedRequestDto;
import com.server.backend.DTO.Institute.ShiftUnitPermittedResponseDto;

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