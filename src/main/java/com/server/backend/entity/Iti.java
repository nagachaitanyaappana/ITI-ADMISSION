package com.server.backend.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Table(name = "iti")
@Data

public class iti {

    @Id
    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "iti_name", nullable = false)
    private String itiName;

    @Column(name = "govt")
    private String govt;

    @Column(name = "dist_code", nullable = false)
    private String distCode;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "allocated")
    private Integer allocated;

    @Column(name = "remaining_capacity")
    private Integer remainingCapacity;

    @Column(name = "tot_strength")
    private Integer totStrength;

    @Column(name = "address")
    private String address;

    @Column(name = "city_town")
    private String cityTown;

    @Column(name = "email")
    private String email;

    @Column(name = "principalname")
    private String principalName;

    @Column(name = "mobile")
    private String mobile;
    @Column(name = "ip_address")
    private String ipAddress;


    @Column(name = "username")

    private String username;


    @Column(name = "password")

    private String password;


    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "iti_type")

    private String itiType;


    @Column(name = "appcode")

    private String appCode;


    @Column(name = "trno")

    private String trNo;
}