
package com.server.backend.controller.Admission;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.backend.entity.Iti;
import com.server.backend.service.Admission.search_iti_by_name_service;

@Tag(name = "Admission Process")
@RestController
@RequestMapping("/admission")
public class search_iti_by_name_controller {

    private final search_iti_by_name_service service;

    public search_iti_by_name_controller(search_iti_by_name_service service) {
        this.service = service;
    }

    @GetMapping("/itis/search/{itiName}")
    public List<Iti> searchItiByName(@PathVariable String itiName) {
        return service.searchItiByName(itiName);
    }
}