package com.server.backend.service.Admission;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.backend.DTO.FreezeUnfreezeRequest;
import com.server.backend.Repository.Admission.candidate_selected_trade_repository;
import com.server.backend.entity.TradeSel2025Phase1;

@Service
public class FreezeUnfreezeServiceImpl
        implements FreezeUnfreezeService {

    private static final Logger logger =
            LoggerFactory.getLogger(FreezeUnfreezeServiceImpl.class);

    private final candidate_selected_trade_repository repository;

    public FreezeUnfreezeServiceImpl(
            candidate_selected_trade_repository repository) {

        this.repository = repository;
    }

    @Override
    @Transactional
    public String updateFreezeStatus(
            FreezeUnfreezeRequest request) {

        if (request == null || request.recordId() == null
                || request.recordId().isBlank()) {
            throw new IllegalArgumentException("recordId is required");
        }

        // 1. The correct table is trade_sel2025phase1 (entity TradeSel2025Phase1).
        // 2. The record identifier column is regid (the primary key).
        Integer regId;
        try {
            regId = Integer.valueOf(request.recordId().trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "recordId must be a numeric regid, got: " + request.recordId());
        }

        // 3. The freeze-status column is freeze. 4. Valid values are 1 = frozen,
        //    0 = unfrozen, matching the legacy freezee convention.
        Integer freezeValue = Boolean.TRUE.equals(request.freeze()) ? 1 : 0;

        TradeSel2025Phase1 record = repository.findById(regId)
                .orElseThrow(() -> new RuntimeException(
                        "Selected trade record not found for regid " + regId));

        record.setFreeze(freezeValue);
        repository.save(record);

        String status = freezeValue == 1 ? "frozen" : "unfrozen";
        logger.info("Regid {} {} (freeze={})", regId, status, freezeValue);

        return "Record " + regId + " " + status + " successfully";
    }

}
