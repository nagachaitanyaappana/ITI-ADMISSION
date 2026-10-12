package com.server.backend.service.Admission;

import com.server.backend.DTO.FreezeUnfreezeRequest;

public interface FreezeUnfreezeService {

    String updateFreezeStatus(FreezeUnfreezeRequest request);

}
