package com.server.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "iti_admissions" , schema = "admissions")
@Data
public class iti_admissions {

    @Column(name = "regid" , length = 15)
    private String regid;

    @Column(name = "name" , length = 50)
    private String name;

    @Column(name = "fname" , length = 50)
    private String fname;

    @Column(name = "addr" , length = 100)
    private String addr;

    @Column(name = "mname" , length = 50)
    private String mname;

    @Column(name = "phno")
    private Long phno ;

    @Column(name = "adarno" , length = 20)
    private String adarno;

    @Column(name = "gender" , length = 10)
    private String gender;

    @Column(name = "caste" , length = 10)
    private String caste;

    @Column(name = "ssc_passed")
    private Boolean ssc_passed;

    @Column(name = "inter_passed")
    private Boolean inter_passed;

    @Column(name = "inter_marks")
    private Integer inter_marks;

    @Column(name = "local" , length = 20)
    private String local;

    @Column(name = "phc")
    private Boolean phc;

    @Column(name = "exservice")
    private Boolean exservice;

    @Column(name = "iti_code" , nullable = false , length = 4)
    private String iti_code;

    @Id
    @Column(name = "adm_num" , nullable = false , length = 14)
    private String adm_num;

    @Column(name = "res_category" , length = 10)
    private String res_category ;

    @Column(name = "year_of_admission" , length = 4)
    private String year_of_admission;

    @Column(name = "current_year")
    private Integer current_year;

    @Column(name = "mode_adm" , length = 60)
    private String mode_adm;

    @Column(name = "fromd_training")
    private LocalDate fromd_training;

    @Column(name = "tod_training")
    private LocalDate tod_training;

    @Column(name = "trade_code")
    private Integer trade_code;

    @Column(name = "dist_code" , nullable = false , length = 2)
    private String dist_code;

    @Column(name = "olddist_code" , length = 2)
    private String olddist_code;

    @Column(name = "olditi_code" , length = 4)
    private String olditi_code;

    @Column(name = "attendance" , length = 3)
    private String attendance;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "pincode" , length = 6)
    private String pincode;

    @Column(name = "idmarks1" , length = 120)
    private String idmarks1;

    @Column(name = "idmarks2" , length = 120)
    private String idmarks2;

    @Column(name = "trno")
    private Long trno;

    @Column(name = "ssc_regno" , length = 20)
    private String ssc_regno;

    @Column(name = "ssc_board" , length = 15)
    private String ssc_board;

    @Column(name = "ssc_year" , length = 4)
    private String ssc_year;

    @Column(name = "ssc_month" , length = 10)
    private String ssc_month;

    @Column(name = "date_of_admission")
    private LocalDate date_of_admission;

    @Column(name = "type_admission" , length = 4)
    private String type_admission;

    @Column(name = "phase")
    private Integer phase;

    @Column(name = "regno" , length = 15)
    private String regno;

    @Column(name = "estcode" , length = 100)
    private String estcode;

    @Column(name = "center_code" , length = 2)
    private String center_code;

    @Column(name = "newadmnum" , length = 15)
    private String newadmnum;

    @Column(name = "final_result")
    private Boolean final_result;

    @Column(name = "final_year_of_pass" , length = 4)
    private String final_year_of_pass;

    @Column(name = "attempt_no")
    private Integer attempt_no = 0;

    @Column(name = "sem_nonsem" , length = 6)
    private String sem_nonsem;

    @Column(name = "dgettrade_code" , length = 3)
    private String dgettrade_code;

    @Column(name = "noof_sems" , length = 1)
    private String noof_sems;

    @Column(name = "finaltrade_code" , length = 7)
    private String finaltrade_code;

    @Column(name = "engg_nonengg" , length = 1)
    private String engg_nonengg;

    @Column(name = "htno" , length = 14)
    private String htno;

    @Column(name = "sem_paper_finalresult")
    private Boolean sem_paper_finalresult;

    @Column(name = "current_sem" , length = 4)
    private String current_sem;

    @Column(name = "oldhtno" , length = 14)
    private String oldhtno;

    @Column(name = "remarks" , length = 100)
    private String remarks;

    @Column(name = "est_code")
    private Integer est_code;

    @Column(name = "email_id" , length = 50)
    private String email_id;

    @Column(name = "shift" , length = 30)
    private String shift;

    @Column(name = "unit" , length = 30)
    private String unit;

    @Column(name = "pwd_category" , length = 1)
    private String pwd_category;

    @Column(name = "economic_weaker_section")
    private Boolean economic_weaker_section;

    @Column(name = "is_trainee_dual_mode")
    private Boolean is_trainee_dual_mode;

    @Column(name = "rec_status" , length = 1)
    private String rec_status;

    @Column(name = "error_description")
    private String error_description;

    @Column(name = "state_reg_number" , length = 20)
    private String state_reg_number;

    @Column(name = "y1")
    private Boolean y1;

    @Column(name = "y2")
    private Boolean y2;

    @Column(name = "internal_marks_y1")
    private Integer internal_marks_y1;

    @Column(name = "external_marks_y1")
    private Integer external_marks_y1;

    @Column(name = "internal_marks_y2")
    private Integer internal_marks_y2;

    @Column(name = "external_marks_y2")
    private Integer external_marks_y2;

    @Column(name = "updateddt")
    private LocalDateTime updateddt;

    @Column(name = "sub_caste")
    private String sub_caste;

}