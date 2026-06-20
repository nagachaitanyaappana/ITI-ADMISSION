package com.server.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "student_application", schema = "public")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class student_application {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "ssc_regno", nullable = false, unique = true, updatable = false)
    private String ssc_regno;

    @Column(name = "regid", nullable = false, unique = true, updatable = false, insertable = false)
    private Integer regid;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "fname", nullable = false, length = 50)
    private String fname;

    @Column(name = "addr", length = 100)
    private String addr;

    @Column(name = "mname", length = 50)
    private String mname;

    @Column(name = "phno", nullable = false)
    private Long phno;

    @Column(name = "adarno", length = 20)
    private String adarno;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "caste", length = 50)
    private String caste;

    @Column(name = "ssc_passed")
    private Boolean ssc_passed;

    @Column(name = "inter_passed")
    private Boolean inter_passed;

    @Column(name = "inter_marks")
    private Integer inter_marks;

    @Column(name = "local", length = 20)
    private String local;

    @Column(name = "pincode", nullable = false)
    private Integer pincode;

    @Column(name = "ts")
    private String ts;

    @Column(name = "ip")
    private String ip;

    @Column(name = "uid")
    private String uid;

    @Column(name = "phc")
    private Boolean phc;

    @Column(name = "exservice")
    private Boolean exservice;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "trno")
    private Long trno;

    @Column(name = "ssc_board")
    private String ssc_board;

    @Column(name = "ssc_year")
    private String ssc_year;

    @Column(name = "ssc_month")
    private String ssc_month;

    @Column(name = "phase")
    private String phase;

    @Column(name = "year", length = 4)
    private String year;

    @Column(name = "university_area", length = 50)
    private String university_area;

    @Column(name = "data_flag", length = 1)
    private String data_flag;

    @Column(name = "app_status", length = 1)
    private String app_status;

    @Column(name = "user_id", length = 50)
    private String user_id;

    @Column(name = "reason", length = 100)
    private String reason;

    @Column(name = "ssc_type", length = 50)
    private String ssc_type;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "pwd_category", length = 1)
    private String pwd_category;

    @Column(name = "economic_weaker_section")
    private Boolean economic_weaker_section;

    @Column(name = "is_trainee_dual_mode")
    private Boolean is_trainee_dual_mode;

    @Column(name = "entry_date")
    private LocalDateTime entry_date;

    @Column(name = "verified_date")
    private LocalDateTime verified_date;

    @Column(name = "verified_ip", length = 50)
    private String verified_ip;

    @Column(name = "ssc_eq_marks", length = 30)
    private String ssc_eq_marks;

    @Column(name = "tc", length = 30)
    private String tc;

    @Column(name = "caste_certificate", length = 30)
    private String caste_certificate;

    @Column(name = "ph_challenge", length = 30)
    private String ph_challenge;

    @Column(name = "ex_service", length = 30)
    private String ex_service;

    @Column(name = "study_certificate", length = 30)
    private String study_certificate;

    @Column(name = "nativity_certificate", length = 30)
    private String nativity_certificate;

    @Column(name = "uidv", length = 30)
    private String uidv;

    @Column(name = "ewsv", length = 30)
    private String ewsv;

    @Column(name = "age_21", length = 30)
    private String age_21;

    @Column(name = "experience", length = 30)
    private String experience;

    @Column(name = "est", length = 30)
    private String est;

    @Column(name = "sub_caste", length = 20)
    private String sub_caste;
}
