package com.server.backend.DTO.Institute;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.Pattern;

@Data
public class ItiCreateDto {
@NotBlank(message = "ITI Code is required")
    private String itiCode;
    @Size(max=255)
    @NotBlank(message = "ITI Name is required")
    private String itiName;
    @NotBlank(message="Govt field is required")
    private String govt;
    @NotBlank(message="District Code is required")
    private String distCode;
    @Size(max=6)
    private String itiNoniti;
    @Size(max=4)
    private String olditicode;

    private Integer capacity;
    private Integer allocated;
    private Integer remainingCapacity;
    private Integer totStrength;
    private LocalDateTime ts;
    private String ipAddress;
    @Size(max=50)
    private String username;
    @Size(max=50)
    private String password;
    private String address;
    private String cityTown;

    private Integer mandCode;
    private Integer pinCode;

    @Email
    private String email;

    private String principalName;
    private Integer roleId;
    private String description;

    @Pattern(regexp="^[0-9]{10}$")
    private String mobile;

    private String landlineNumber;
    private String yearEst;
    private Integer trNo;

    private String itiType;
    private String appCode;

    private Boolean vtp;
    @Size(max=20)
    private String vtpRegno;

    private String land;
    private String builtupArea;

    private Integer noofToilets;
    private Boolean availableDrinkingwater;
    private Integer noofLabs;
    private Integer noofClassrooms;
    private Integer examconductingStrength;
}
    