package com.server.backend.controller.MeritChecklist;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import com.server.backend.service.MeritChecklist.CasteMasterService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;
import java.util.HashMap;
@Tag(name = "Merit & Checklist")
@RestController
@RequestMapping("/api/dsc")
@CrossOrigin(origins="http://localhost:5052")
public class CasteMasterController {
    private final CasteMasterService casteMasterService;

    public CasteMasterController(CasteMasterService casteMasterService) {
        this.casteMasterService = casteMasterService;
    }
    @GetMapping("/caste-list")
    public Map<String, Object> getCasteList() {
    Map<String, Object> response = new HashMap<>();
    response.put("success", "true");
    response.put("data", casteMasterService.getAllCasteMasters());
    return response;
    }
    
}
