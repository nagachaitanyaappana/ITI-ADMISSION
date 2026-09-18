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
import com.server.backend.entity.MeritList;
import com.server.backend.entity.MeritListId;
import com.server.backend.service.MeritChecklist.MeritListService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Merit & Checklist")
@RestController
@RequestMapping("/api/meritlist")
@CrossOrigin(origins="http://localhost:5052")
public class MeritListController {


    private final MeritListService meritListService;



    public MeritListController(MeritListService meritListService) {
        this.meritListService = meritListService;
    }
    @Operation(summary = "Get all merit lists")
    @GetMapping
    public List<MeritList> getAllMeritList() {
        return meritListService.getAllMeritList();
    }
    @Operation(summary = "Get merit list by registration ID")
    @GetMapping("/{regid}")
    public MeritList getMeritListByRegId(@PathVariable Integer regid){
        return meritListService.getMeritListByRegId(regid);
    }

    @Operation(summary = "Get merit list by district code")
    @GetMapping("/district/{dist_code}")
    public List<MeritList> getMeritListByDistCode(
            @PathVariable String dist_code){
        return meritListService.getMeritListByDistCode(dist_code);
    }

    @Operation(summary = "Get merit list by phase")
    @GetMapping("/phase/{phase}")
    public List<MeritList> getMeritListByPhase(
            @PathVariable String phase) {
        return meritListService.getMeritListByPhase(phase);
    }

    @Operation(summary = "Get merit list by ITI code")
    @GetMapping("/iti/{iti_code}")
    public List<MeritList> getMeritListByItiCode(
            @PathVariable String iti_code){
        return meritListService.getMeritListByItiCode(iti_code);
    }

    @Operation(summary = "Get merit list by application status")
    @GetMapping("/status/{app_status}")
    public List<MeritList> getMeritListByAppStatus(
            @PathVariable String app_status){
                if("null".equalsIgnoreCase(app_status)) {
                    return meritListService.getMeritListByAppStatusIsNull();
                }
        return meritListService.getMeritListByAppStatus(app_status);
    }
    @Operation(summary = "Create a new merit list")
    @PostMapping
public MeritList createMeritList(@RequestBody MeritList meritList) {
    
    return meritListService.saveMeritList(meritList);
}
@Operation(summary = "Update an existing merit list")
@PutMapping("/{regid}")
public MeritList updateMeritList(@PathVariable Integer regid, @RequestBody MeritList meritList) {

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
            MeritListId meritListId = new MeritListId(regid, qual, temp_pk, phase);
            meritListService.deleteMeritList(meritListId);
        }
@Operation(summary = "Generate/Regenerate merit lists or checklists")
@PostMapping("/generate")
public ResponseEntity<MeritListResponse> generateMeritList(
        @RequestBody MeritListRequest request,
        @RequestHeader(name = "X-Role-Id", defaultValue = "3") String roleId,
        @RequestHeader(name = "X-Iti-Code", defaultValue = "ITI001") String itiCode,
        @RequestHeader(name = "X-Dist-Code", defaultValue = "DIST001") String distCode
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

        UserPrincipal user = new UserPrincipal(roleId, itiCode, distCode);
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

}

