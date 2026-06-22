package com.server.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.DTO.Institute.ItiDto;
import com.server.backend.DTO.Institute.ItiPatchDto;
import com.server.backend.entity.Iti;
import com.server.backend.service.ItiService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/itis")
public class itiController {

    private final ItiService itiService;

    public itiController(ItiService itiService) {
        this.itiService = itiService;
    }

    @GetMapping
    public List<Iti> getAllItis() {
        return itiService.getAllItis();
    }

    @GetMapping("/{itiCode}")
    public Iti getItiByCode(
            @PathVariable String itiCode) {

        return itiService.getItiByCode(itiCode);
    }

    //here
    /* @GetMapping("/create")
    public String createItiPage(Model model) {
        model.addAttribute("districtList", itiService.getDistrictOptions());
        return "iti-create"; // name of Thymeleaf template iti-create.html
    }*/

   @PostMapping
    public Iti createIti(
            @Valid @RequestBody ItiDto dto) {

        return itiService.createIti(dto);
    }

    @PutMapping("/{itiCode}")
    public Iti updateIti(
            @PathVariable String itiCode,
           @Valid @RequestBody ItiDto dto) {

        return itiService.updateIti(itiCode, dto);
    }

    @DeleteMapping("/{itiCode}")
    public String deleteIti(
            @PathVariable String itiCode) {

        itiService.deleteIti(itiCode);

        return "ITI Deleted Successfully";
            }

      @GetMapping("/{itiCode}/{distCode}")
      public ResponseEntity<Iti> getItiByCodeAndDistCode(
        @PathVariable String itiCode,
        @PathVariable String distCode) {

    Iti iti = itiService.getItiByCodeAndDistCode(
            itiCode,
            distCode);

    return ResponseEntity.ok(iti);
}
@PatchMapping("/{itiCode}/{distCode}")
public ResponseEntity<Iti> patchIti(
        @PathVariable String itiCode,
        @PathVariable String distCode,
        @RequestBody ItiPatchDto dto) {

    Iti updatedIti =
            itiService.patchIti(
                    itiCode,
                    distCode,
                    dto);

    return ResponseEntity.ok(updatedIti);
}
}