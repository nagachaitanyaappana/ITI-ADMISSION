package com.server.backend.DTO.Institute;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Map;

@Data
public class ItiDto {
    
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

    private Map<String,String> strength;
    private Map<String,String> strengthVacant;
    private Map<String,String> strengthFill;
    private Boolean admissionPermission;

    private String dgetItiCode;
    private String region;
    private String urbanRural;
    private String webItiCode;

    private Boolean toolStatus;

    private String recStatus;
    @Size(max = 150,message = "Website URL should not exceed 255 characters")
    private String website;
    private String gradePoint;
    private String ncvtCode;

    private String mandalCode;
    private String jnanabhumicode;

    private String parliamentConstituencyName;
    private String assemblyConstituencyName;
}