package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "login_users", schema = "public")
@Data
public class LoginUser {

    @Id
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "username", length = 100)
    private String userName;

    @Column(name = "roleid")
    private Integer roleId;

    @Column(name = "ins_code", length = 4)
    private String distCode;

    @Column(name = "iti_code", length = 4)
    private String itiCode;

    @Column(name = "mobile", length = 15)
    private String mobile;

    @Column(name = "email", length = 100)
    private String email;
}