package com.server.backend.controller.Admission;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.server.backend.DTO.FreezeUnfreezeRequest;
import com.server.backend.service.Admission.FreezeUnfreezeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/admissions")
public class FreezeUnfreezeController {

    private final FreezeUnfreezeService freezeUnfreezeService;

    public FreezeUnfreezeController(
            FreezeUnfreezeService freezeUnfreezeService) {
        this.freezeUnfreezeService = freezeUnfreezeService;
    }

    @PostMapping("/freeze-unfreeze")
    public ResponseEntity<String> updateFreezeStatus(
            @Valid @RequestBody FreezeUnfreezeRequest request) {

        String result =
                freezeUnfreezeService.updateFreezeStatus(request);

        return ResponseEntity.ok(result);
    }

}
