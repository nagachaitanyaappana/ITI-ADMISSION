package com.server.backend.controller.Admission;

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

import com.server.backend.DTO.student_trade_selection_dto;
import com.server.backend.DTO.student_trade_selection_request_dto;
import com.server.backend.service.Admission.student_trade_selection_service;

/**
 * Candidate-side ITI selection — the write path into {@code student_trade_sel}.
 *
 * <p>Ported from {@code applicationForm.open_Application_InterfaceAction:321}. Together with
 * {@code /api/trade-selection} (which writes the promoted copy {@code trade_sel}) this closes the
 * selection flow: candidate picks ITIs here, the district/ITI verifier promotes the row.
 *
 * <p>The candidate flow is unauthenticated and keyed on the registration id the candidate types
 * (see {@code /student-apply}), so the regid travels in the path, exactly like the existing
 * {@code PUT /api/student/update/{regid}}.
 */
@Tag(name = "Admission Process")
@RestController
@RequestMapping("/admission/student-trade-selection")
public class student_trade_selection_controller {

    private final student_trade_selection_service service;

    public student_trade_selection_controller(student_trade_selection_service service) {
        this.service = service;
    }

    @Operation(summary = "List a candidate's saved ITI selections",
            description = "Reads student_trade_sel joined to iti/dist_mst for display names.")
    @GetMapping("/{regId}")
    public List<student_trade_selection_dto> list(@PathVariable Integer regId) {
        return service.getSelections(regId);
    }

    @Operation(summary = "Replace a candidate's ITI selections",
            description = "Replaces every student_trade_sel row for the candidate with the posted "
                    + "choices (max 60). Writes year from iti_params code '7' and the current phase "
                    + "from admissions.admission_phase. Mirrors legacy open_Application_InterfaceAction.")
    @PostMapping("/{regId}")
    public ResponseEntity<Map<String, Object>> save(
            @PathVariable Integer regId,
            @RequestBody student_trade_selection_request_dto request) {
        return ResponseEntity.ok(service.saveSelections(regId, request));
    }

    @Operation(summary = "Remove one ITI from a candidate's selection",
            description = "Mirrors the legacy per-row Delete link (web/getIti_list.jsp).")
    @DeleteMapping("/{regId}/{itiCode}")
    public ResponseEntity<Map<String, Object>> remove(
            @PathVariable Integer regId,
            @PathVariable String itiCode) {
        return ResponseEntity.ok(service.removeSelection(regId, itiCode));
    }

    @Operation(summary = "Clear a candidate's ITI selections")
    @DeleteMapping("/{regId}")
    public ResponseEntity<Map<String, Object>> clear(@PathVariable Integer regId) {
        return ResponseEntity.ok(service.clearSelections(regId));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, Object>> handleBadRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("success", false, "error", ex.getMessage()));
    }
}
