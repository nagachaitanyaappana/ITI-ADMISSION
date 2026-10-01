package com.server.backend.controller.ITI;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.TradeSelectionRequestDto;
import com.server.backend.service.ITI.TradeSelectionService;

/**
 * Trade selection — the write path into {@code trade_sel}.
 *
 * <p>Ported from the legacy {@code Master.Trade.Tradesel_entry_Action}. Without this endpoint
 * {@code trade_sel} stays empty and merit generation can never find a candidate, because the
 * generator filters on {@code regid in (select regid from trade_sel where ...)}.
 * <p>Lives in the {@code ITI} module alongside the ITI-side lookups a selection page needs
 * (eligible ITIs, trades by ITI, candidate profile). The table itself is ranked by the
 * {@code meritlistchecklist} module, which is why the legacy file sat under {@code Master.Trade}.
 */
@Tag(name = "ITI", description = "Institute / ITI admin masters: designations, districts, itis, trades, shift units")
@RestController
@RequestMapping("/api/trade-selection")
public class TradeSelectionController {

    private final TradeSelectionService service;

    public TradeSelectionController(TradeSelectionService service) {
        this.service = service;
    }

    @Operation(summary = "Record a candidate's trade selection",
            description = "Writes trade_sel and appends the current phase key to application.phase. "
                    + "Mirrors legacy Tradesel_entry_Action. Set freezee=1 to freeze immediately.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(
            @RequestBody TradeSelectionRequestDto request) {
        return ResponseEntity.ok(service.saveSelection(request,
                request == null ? null : request.getItiCode(),
                request == null ? null : request.getDistCode(),
                request == null ? null : request.getFreezee()));
    }

    @Operation(summary = "Remove a candidate's phase membership in trade_sel",
            description = "Equivalent to the legacy delete branch: phase = delete(phase, array[phase]). "
                    + "The row is kept so the choice can be re-confirmed.")
    @DeleteMapping("/{regId}/{itiCode}")
    public ResponseEntity<Map<String, Object>> remove(
            @PathVariable Integer regId,
            @PathVariable String itiCode) {
        return ResponseEntity.ok(service.removeSelection(regId, itiCode));
    }

    @Operation(summary = "List a candidate's recorded trade selections")
    @GetMapping("/{regId}")
    public List<Map<String, Object>> list(@PathVariable Integer regId) {
        return service.getSelections(regId);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, Object>> handleBadRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("success", false, "error", ex.getMessage()));
    }
}