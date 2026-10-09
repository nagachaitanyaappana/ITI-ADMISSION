package com.server.backend.DTO;

import lombok.Data;

@Data
public class AllResourceRoleResponse {
    private String roleName;
    private String userName;
    private String distName;
    private String itiName;
    private String mobile;
    private String email;

    public AllResourceRoleResponse(String roleName, String userName, String distName,
            String itiName, String mobile, String email) {
        this.roleName = roleName;
        this.userName = userName;
        this.distName = distName;
        this.itiName = itiName;
        this.mobile = mobile;
        this.email = email;
    }
}