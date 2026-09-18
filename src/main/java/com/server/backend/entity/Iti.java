package com.server.backend.entity;
import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLHStoreType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "iti", schema = "public")
@Data
public class Iti {

    @Id
    @Column(name = "iti_code", length = 255)
    private String itiCode;

    @Column(name = "iti_name",length=255, nullable = false)
    private String itiName;

    @Column(name = "govt", length = 1)
    private String govt;

    @Column(name = "dist_code",length=2, nullable = false)
    private String distCode;

     @Column(name = "iti_noniti" ,length=6)
    private String itiNoniti;

    @Column(name = "olditicode", length = 4)
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
 
    @Column(name = "address" ,length=255)
    private String address;

    @Column(name = "city_town", length = 255)
    private String cityTown;
      
    @Column(name = "mand_code")
    private Integer mandCode;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "principalname", length = 255)
    private String principalName;
    private Integer roleId;
    private String description;
    @Column(name = "mobile", length = 255)
    private String mobile;

    @Column(name = "landlinenumber", length = 15)
     private String landlineNumber;
    @Column(name = "year_est",length=6)
     private String yearEst;
    
    @Column(name = "ip_address",length=50)
    private String ipAddress;

    @Column(name = "username",length=50)
    private String username;


    @Column(name = "password",length=50)
    private String password;


    @Column(name = "pin_code")
    private Integer pinCode;

    @Column(name = "iti_type",length=1)

    private String itiType;


    @Column(name = "appcode",length=2)

    private String appCode;

   @Column(name = "vtp")
   private Boolean vtp;

  @Column(name = "vtp_regno", length = 20)
  private String vtpRegno;

  @Column(name = "land",length=10)
   private String land;
 @Column(name = "trno")
 private Integer trNo;
 @Column(name = "builtup_area" ,length=10)
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




@Type(PostgreSQLHStoreType.class)
@Column(name = "strength", columnDefinition = "hstore")
private Map<String, String> strength;

@Type(PostgreSQLHStoreType.class)
@Column(name = "strength_vacant", columnDefinition = "hstore")
private Map<String, String> strengthVacant;

@Type(PostgreSQLHStoreType.class)
@Column(name = "strength_fill", columnDefinition = "hstore")
private Map<String, String> strengthFill;


@Column(name = "admission_permission")
private Boolean admissionPermission;

@Column(name = "dget_iti_code" ,length=10)
private String dgetItiCode;

@Column(name = "region", length = 5)
private String region;

@Column(name = "urban_rural",length = 2)
private String urbanRural;

@Column(name = "web_iti_code",length=8)
private String webItiCode;

@Column(name = "tool_status")
private Boolean toolStatus;

@Column(name = "rec_status",length=1)
private String recStatus;

@Column(name = "website", length = 150)
private String website;

@Column(name = "grade_point", length = 35)
private String gradePoint;

@Column(name = "ncvt_code", length = 25)
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