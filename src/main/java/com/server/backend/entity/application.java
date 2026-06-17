package com.server.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLHStoreType;

import lombok.Data;

@Entity
@Table(name = "application", schema = "public")
@Data
public class application {

    @Id
    @Column(name = "regid" , nullable = false)
    private Integer regid;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "fname" , nullable = false)
    private String fname;

    @Column(name = "addr")
    private String addr;

    @Column(name = "mname" , nullable = false)
    private String mname;

    @Column(name = "phno" , nullable = false)
    private Long phno;

    @Column(name = "adarno" , nullable = false)
    private String adarno;

    @Column(name = "gender" , nullable = false)
    private String gender;

    @Column(name = "caste" , nullable = false)
    private String caste;

    @Column(name = "ssc_passed")
    private Boolean ssc_passed;

    @Column(name = "inter_passed")
    private Boolean inter_passed;

    @Column(name = "inter_marks")
    private Integer inter_marks;

    @Column(name = "local")
    private String local;

    @Column(name = "pincode")
    private Integer pincode ;

    @Column(name = "ts")
    private String ts;

    @Column(name = "ip")
    private String ip;

    @Column(name = "uid")
    private String uid;

    @Column(name = "phc" , nullable = false)
    private Boolean phc;

    @Column(name = "exservice" , nullable = false)
    private Boolean exservice;

    @Column(name = "dob" , nullable = false)
    private LocalDate dob;

    @Column(name = "trno")
    private Long trno;

    @Column(name = "ssc_regno")
    private String ssc_regno;

    @Column(name = "ssc_board")
    private String ssc_board;

    @Column(name = "ssc_year")
    private String ssc_year;

    @Column(name = "ssc_month")
    private String ssc_month;

    @Type(PostgreSQLHStoreType.class)
    @Column(name = "phase", columnDefinition = "hstore")
    private Map<String, String> phase = new HashMap<>();

    @Column(name = "year")
    private String year;

    @Column(name = "university_area")
    private String university_area;

    @Column(name = "data_flag")
    private String data_flag;

    @Column(name = "app_status")
    private String app_status;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "ssc_type")
    private String ssc_type;

    @Column(name = "reason")
    private String reason;

    @Column(name = "email")
    private String email;

    @Column(name = "pwd_category")
    private String pwd_category;

    @Column(name = "economic_weaker_section")
    private Boolean economic_weaker_section;

    @Column(name = "is_trainee_dual_mode")
    private Boolean is_trainee_dual_mode;

    @Column(name = "entry_date")
    private LocalDateTime entry_date;

    @Column(name = "verified_date")
    private LocalDateTime verified_date;

    @Column(name = "verified_ip")
    private String verified_ip;

    @Column(name = "ssc_eq_marks")
    private String ssc_eq_marks;

    @Column(name = "tc")
    private String tc;

    @Column(name = "caste_certificate")
    private String caste_certificate;

    @Column(name = "ph_challenge")
    private String ph_challenge;

    @Column(name = "ex_service")
    private String ex_service;

    @Column(name = "study_certificate")
    private String study_certificate;

    @Column(name = "nativity_certificate")
    private String nativity_certificate;

    @Column(name = "uidv")
    private String uidv;

    @Column(name = "ewsv")
    private String ewsv;

    @Column(name = "age_21")
    private String age_21;

    @Column(name = "experience")
    private String experience;

    @Column(name = "est")
    private String est;

    @Column(name = "sub_caste")
    private String sub_caste;

}