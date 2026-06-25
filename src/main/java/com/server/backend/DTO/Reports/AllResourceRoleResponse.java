package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllResourceRoleResponse {
    private String roleName;
    private String userName;
    private String distName;
    private String itiName;
    private String mobile;
    private String email;
}