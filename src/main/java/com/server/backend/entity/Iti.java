package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;
@Entity
@Table(name = "iti")
@Data
public class Iti {

    @Id
    @Column(name = "iti_code")
    private String itiCode;

    @Column(name = "iti_name", nullable = false)
    private String itiName;

    @Column(name = "govt")
    private String govt;

    @Column(name = "dist_code", nullable = false)
    private String distCode;

     @Column(name = "iti_noniti")
    private String itiNoniti;

    @Column(name = "olditicode")
    private String olditicode;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "allocated")
    private Integer allocated;

    @Column(name = "remaining_capacity")
    private Integer remainingCapacity;

    @Column(name = "tot_strength")
    private Integer totStrength;
    @Column(name="ts")
    private LocalDateTime ts;
 
    @Column(name = "address")
    private String address;

    @Column(name = "city_town")
    private String cityTown;
      
    @Column(name = "mand_code")
    private Integer mandCode;

    @Column(name = "email")
    private String email;

    @Column(name = "principalname")
    private String principalName;
    @Column(name="role_id")
    private Integer roleId;
    @Column(name = "description")

    private String description;
    @Column(name = "landlinenumber")
    private String landlineNumber;
    @Column(name = "year_est")
     private String yearEst;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;


    @Column(name = "pin_code")
    private Integer pinCode;

    @Column(name = "iti_type")

    private String itiType;


    @Column(name = "appcode")

    private String appCode;

   @Column(name = "vtp")
   private Boolean vtp;

  @Column(name = "vtp_regno")
  private String vtpRegno;

  @Column(name = "land")
   private String land;
 @Column(name = "trno")
 private Integer trNo;
 @Column(name = "builtup_area")
 private String builtupArea;

@Column(name = "noof_toilets")
private Integer noofToilets;

@Column(name = "available_drinkingwater")
private Boolean availableDrinkingwater;

@Column(name = "noof_labs")
private Integer noofLabs;

@Column(name = "noof_classrooms")
private Integer noofClassrooms;

@Column(name = "examconducting_strength")
private Integer examconductingStrength;

@Column(name = "strength")
private String strength;

@Column(name = "strength_vacant")
private String strengthVacant;

@Column(name = "strength_fill")
private String strengthFill;

@Column(name = "admission_permission")
private Boolean admissionPermission;

@Column(name = "dget_iti_code")
private String dgetItiCode;

@Column(name = "region")
private String region;

@Column(name = "urban_rural")
private String urbanRural;

@Column(name = "web_iti_code")
private String webItiCode;

@Column(name = "tool_status")
private Boolean toolStatus;

@Column(name = "rec_status")
private String recStatus;

@Column(name = "website")
private String website;

@Column(name = "grade_point")
private String gradePoint;

@Column(name = "ncvt_code")
private String ncvtCode;

@Column(name = "mandal_code")
private String mandalCode;

@Column(name = "jnanabhumicode")
private String jnanabhumicode;

@Column(name = "parliament_constituency_name")
private String parliamentConstituencyName;

@Column(name = "assembly_constituency_name")
private String assemblyConstituencyName;
}