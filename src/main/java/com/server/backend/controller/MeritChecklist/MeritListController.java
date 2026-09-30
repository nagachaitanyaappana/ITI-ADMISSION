package com.server.backend.controller.MeritChecklist;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.server.backend.DTO.UserPrincipal;
import com.server.backend.DTO.MeritListRequest;
import com.server.backend.DTO.MeritListResponse;
import com.server.backend.DTO.MeritListRow;
import com.server.backend.entity.RankEntity;
import com.server.backend.entity.RankId;
import com.server.backend.service.MeritChecklist.MeritListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Merit & Checklist")
@RestController
@RequestMapping("/api/meritlist")
public class MeritListController {


    private final MeritListService meritListService;



    public MeritListController(MeritListService meritListService) {
        this.meritListService = meritListService;
    }
    @Operation(summary = "Get all merit lists")
    @GetMapping
    public List<RankEntity> getAllMeritList() {
        return meritListService.getAllMeritList();
    }
    @Operation(summary = "Get merit list by registration ID")
    @GetMapping("/{regid}")
    public RankEntity getMeritListByRegId(@PathVariable Integer regid){
        return meritListService.getMeritListByRegId(regid);
    }

    @Operation(summary = "Get merit list by district code")
    @GetMapping("/district/{dist_code}")
    public List<RankEntity> getMeritListByDistCode(
            @PathVariable String dist_code){
        return meritListService.getMeritListByDistCode(dist_code);
    }

    @Operation(summary = "Get merit list by phase")
    @GetMapping("/phase/{phase}")
    public List<RankEntity> getMeritListByPhase(
            @PathVariable String phase) {
        return meritListService.getMeritListByPhase(phase);
    }

    @Operation(summary = "Get merit list by ITI code")
    @GetMapping("/iti/{iti_code}")
    public List<RankEntity> getMeritListByItiCode(
            @PathVariable String iti_code){
        return meritListService.getMeritListByItiCode(iti_code);
    }

    @Operation(summary = "Get merit list by application status")
    @GetMapping("/status/{app_status}")
    public List<RankEntity> getMeritListByAppStatus(
            @PathVariable String app_status){
                if("null".equalsIgnoreCase(app_status)) {
                    return meritListService.getMeritListByAppStatusIsNull();
                }
        return meritListService.getMeritListByAppStatus(app_status);
    }
    @Operation(summary = "Create a new merit list")
    @PostMapping
public RankEntity createMeritList(@RequestBody RankEntity meritList) {
    
    return meritListService.saveMeritList(meritList);
}
@Operation(summary = "Update an existing merit list")
@PutMapping("/{regid}")
public RankEntity updateMeritList(@PathVariable Integer regid, @RequestBody RankEntity meritList) {

    meritList.setRegid(regid);
    return meritListService.updateMeritList(meritList);
}
@Operation(summary = "Delete a merit list")
@DeleteMapping("/{regid}")
public void deleteMeritList(
        @PathVariable Integer regid,
        @RequestParam String qual,
        @RequestParam String temp_pk,
        @RequestParam String phase)
        {
            RankId meritListId = new RankId(regid, qual, temp_pk, phase);
            meritListService.deleteMeritList(meritListId);
        }
@Operation(summary = "Generate/Regenerate merit lists or checklists")
@PostMapping("/generate")
public ResponseEntity<MeritListResponse> generateMeritList(
        @RequestBody MeritListRequest request,
        @RequestHeader(name = "X-Role-Id", required = false) String roleId,
        // No defaultValue on purpose. These previously defaulted to the placeholders
        // "ITI001" / "DIST001", so a request without them silently generated rows tagged with a
        // non-existent ITI/district instead of failing. MeritListService already rejects a blank
        // institution code, which is the behaviour we want.
        // login_users has a single ins_code column holding either a district or an ITI code
        // depending on role (verified: role 3 -> 18/18 district, role 4 -> 528/600 ITI), so the
        // page sends it once as X-Ins-Code and resolveUser() maps it to the right slot.
        @RequestHeader(name = "X-Ins-Code", required = false) String insCode,

        @RequestHeader(name = "X-Iti-Code", required = false) String itiCode,
        @RequestHeader(name = "X-Dist-Code", required = false) String distCode
) {
    try {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MeritListResponse(false, "Request body is required."));
        }
        if (request.status() == null || request.status().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MeritListResponse(false, "Status is required."));
        }

        UserPrincipal user = resolveUser(roleId, insCode, itiCode, distCode);
        Map<String, Object> serviceResult = meritListService.generateMeritList(
               
                request.category(),
                request.qual(),
                request.status(),
                user
        );

        String heading = (String) serviceResult.get("heading");
        @SuppressWarnings("unchecked")
        List<MeritListRow> data = (List<MeritListRow>) serviceResult.get("data");

        return ResponseEntity.ok(new MeritListResponse(true, heading, data));

    } catch (IllegalArgumentException | IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MeritListResponse(false, e.getMessage()));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MeritListResponse(false, "An error occurred: " + e.getMessage()));
    }
}


    /**
     * Builds the calling user from the request headers.
     *
     * <p>{@code login_users.ins_code} is a single column that holds either a district code or an
     * ITI code depending on the role, so the page sends it once as {@code X-Ins-Code}. The role
     * decides which field it belongs in. The explicit {@code X-Iti-Code} / {@code X-Dist-Code}
     * headers still win when supplied, so an admin tool can target an arbitrary scope.
     *
     * @throws IllegalArgumentException when the role is missing or no institution code came through,
     *                                  rather than silently generating for a placeholder institution
     */
    private UserPrincipal resolveUser(String roleId, String insCode, String itiCode, String distCode) {
        String role = (roleId == null || roleId.isBlank()) ? null : roleId.trim();
        String ins = (insCode == null || insCode.isBlank()) ? null : insCode.trim();

        if (role == null) {
            throw new IllegalArgumentException(
                    "X-Role-Id is required so the merit list can be scoped to a district or an ITI");
        }

        String resolvedIti = (itiCode == null || itiCode.isBlank()) ? null : itiCode.trim();
        String resolvedDist = (distCode == null || distCode.isBlank()) ? null : distCode.trim();

        if ("3".equals(role)) {
            // District login: the institution code is the district.
            if (resolvedDist == null) {
                resolvedDist = ins;
            }
        } else if (resolvedIti == null) {
            // ITI login (and any other role): the institution code is the ITI.
            resolvedIti = ins;
        }

        if (resolvedIti == null && resolvedDist == null) {
            throw new IllegalArgumentException(
                    "X-Ins-Code is required so the merit list can be scoped to a district or an ITI");
        }
        return new UserPrincipal(role, resolvedIti, resolvedDist);
    }
}
