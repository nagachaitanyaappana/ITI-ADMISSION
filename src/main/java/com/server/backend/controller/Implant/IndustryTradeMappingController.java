package com.server.backend.controller.Implant;

import com.server.backend.DTO.Implant.IndustryTradeMappingRequest;
import com.server.backend.DTO.Implant.IndustryTradeMappingResponse;
import com.server.backend.service.Implant.IndustryTradeMappingService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.Long;
import java.util.List;
@RestController
@RequestMapping("/api/placements/industry-trade-mapping")
@Tag(
    name = "Industry Trade Mapping Controller",
    description = "APIs for mapping industries with trades for an ITI"
)
public class IndustryTradeMappingController {

    private final IndustryTradeMappingService mappingService;

    public IndustryTradeMappingController(
            IndustryTradeMappingService mappingService) {

        this.mappingService = mappingService;
    }

    @PostMapping
    public ResponseEntity<String> saveMapping(
            @RequestBody IndustryTradeMappingRequest request) {

        return ResponseEntity.ok(
                mappingService.saveIndustryTradeMapping(request)
        );
    }
    @PutMapping("/{slno}")
public ResponseEntity<String> updateMapping(
        @PathVariable Long slno,
        @RequestBody IndustryTradeMappingRequest request) {

    return ResponseEntity.ok(
            mappingService.updateIndustryTradeMapping(
                    slno,
                    request
            )
    );
}
@GetMapping
public ResponseEntity<List<IndustryTradeMappingResponse>>
getAllMappings() {

    return ResponseEntity.ok(
            mappingService.getAllIndustryTradeMappings()
    );
}
@GetMapping("/{slno}")
public ResponseEntity<IndustryTradeMappingResponse> getMapping(
        @PathVariable Long slno) {

    return ResponseEntity.ok(
            mappingService.getIndustryTradeMapping(slno)
    );
}
@DeleteMapping("/{slno}")
public ResponseEntity<String> deleteMapping(
        @PathVariable Long slno) {

    mappingService.deleteIndustryTradeMapping(slno);

    return ResponseEntity.ok(
            "Industry and trade mapping deleted successfully"
    );
}
}