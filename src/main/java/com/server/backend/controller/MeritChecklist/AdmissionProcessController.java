package com.server.backend.controller.MeritChecklist;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.RankLookupRequest;
import com.server.backend.DTO.TakeAdmissionRequest;
import com.server.backend.DTO.UserPrincipal;
import com.server.backend.service.MeritChecklist.AdmissionProcessService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The endpoints {@code /AdmissionPhase} ("Admission Phase 1" in the navbar) builds and calls.
 *
 * <p>Before this controller existed the page called three paths that returned 404 and three that
 * landed on {@code POST /admission-timings} — the "create a schedule row" route — which answered 400
 * for every payload the page sent. The page hid all of it behind {@code console.error}, which is why
 * it still rendered as a 200 page with an empty grid.
 *
 * <p>Caller identity arrives as headers, the convention already used by {@code MeritListController}
 * and {@code AdmissionTimingController}: the JSP layer and this backend are separate applications, so
 * this side never sees the login session.
 */
@Tag(name = "Admission Process", description = "Admission Phase 1: boards, seat matrix, rank lookup, admission entry")
@RestController
@RequestMapping("/api/admission")
public class AdmissionProcessController {

    private final AdmissionProcessService admissionProcessService;

    public AdmissionProcessController(AdmissionProcessService admissionProcessService) {
        this.admissionProcessService = admissionProcessService;
    }

    @Operation(summary = "Type-of-admission (board) list for the approval dropdown")
    @GetMapping("/iti-boards")
    public ResponseEntity<Map<String, Object>> getItiBoards(
            @RequestHeader(name = "X-Role-Id", required = false) String roleId,
            @RequestHeader(name = "X-Ins-Code", required = false) String insCode,
            @RequestHeader(name = "X-Iti-Code", required = false) String itiCode,
            @RequestHeader(name = "X-Dist-Code", required = false) String distCode) {
        return handle(() -> {
            resolveUser(roleId, insCode, itiCode, distCode);
            return admissionProcessService.getItiBoards();
        });
    }

    @Operation(summary = "Seat matrix (T/F/V per category) for the logged-in ITI or district")
    @PostMapping("/iti-seat-matrix")
    public ResponseEntity<Map<String, Object>> getSeatMatrix(
            @RequestHeader(name = "X-Role-Id", required = false) String roleId,
            @RequestHeader(name = "X-Ins-Code", required = false) String insCode,
            @RequestHeader(name = "X-Iti-Code", required = false) String itiCode,
            @RequestHeader(name = "X-Dist-Code", required = false) String distCode) {
        return handle(() -> admissionProcessService.getSeatMatrix(
                resolveUser(roleId, insCode, itiCode, distCode)));
    }

    @Operation(summary = "Resolve a merit rank to the candidate holding it")
    @PostMapping("/rank-lookup")
    public ResponseEntity<Map<String, Object>> lookupRank(
            @RequestBody(required = false) RankLookupRequest request,
            @RequestHeader(name = "X-Role-Id", required = false) String roleId,
            @RequestHeader(name = "X-Ins-Code", required = false) String insCode,
            @RequestHeader(name = "X-Iti-Code", required = false) String itiCode,
            @RequestHeader(name = "X-Dist-Code", required = false) String distCode) {
        return handle(() -> admissionProcessService.lookupRank(
                request == null ? null : request.rank(),
                resolveUser(roleId, insCode, itiCode, distCode)));
    }

    @Operation(summary = "Record an admission for the candidate and fill the seat")
    @PostMapping("/take-admission")
    public ResponseEntity<Map<String, Object>> takeAdmission(
            @RequestBody(required = false) TakeAdmissionRequest request,
            @RequestHeader(name = "X-Role-Id", required = false) String roleId,
            @RequestHeader(name = "X-Ins-Code", required = false) String insCode,
            @RequestHeader(name = "X-Iti-Code", required = false) String itiCode,
            @RequestHeader(name = "X-Dist-Code", required = false) String distCode) {
        return handle(() -> admissionProcessService.takeAdmission(request,
                resolveUser(roleId, insCode, itiCode, distCode)));
    }

    /** Keeps every endpoint's error shape identical: {@code {success:false, error:"..."}} at 400. */
    private ResponseEntity<Map<String, Object>> handle(
            java.util.function.Supplier<Map<String, Object>> action) {
        try {
            return ResponseEntity.ok(action.get());
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "error", ex.getMessage()));
        }
    }

    /**
     * Builds the caller from the request headers.
     *
     * <p>{@code login_users.ins_code} holds either a district code or an ITI code depending on the
     * role, so the page sends it once as {@code X-Ins-Code} and the role decides which field it
     * belongs to. Explicit {@code X-Iti-Code} / {@code X-Dist-Code} headers still win.
     */
    private UserPrincipal resolveUser(String roleId, String insCode, String itiCode, String distCode) {
        String role = (roleId == null || roleId.isBlank()) ? null : roleId.trim();
        String ins = (insCode == null || insCode.isBlank()) ? null : insCode.trim();

        if (role == null) {
            throw new IllegalArgumentException(
                    "X-Role-Id is required so the admission can be scoped to a district or an ITI");
        }

        String resolvedIti = (itiCode == null || itiCode.isBlank()) ? null : itiCode.trim();
        String resolvedDist = (distCode == null || distCode.isBlank()) ? null : distCode.trim();

        if ("3".equals(role)) {
            if (resolvedDist == null) {
                resolvedDist = ins;
            }
        } else if (resolvedIti == null) {
            resolvedIti = ins;
        }

        if (resolvedIti == null && resolvedDist == null) {
            throw new IllegalArgumentException(
                    "X-Ins-Code is required so the admission can be scoped to a district or an ITI");
        }
        return new UserPrincipal(role, resolvedIti, resolvedDist);
    }
}
