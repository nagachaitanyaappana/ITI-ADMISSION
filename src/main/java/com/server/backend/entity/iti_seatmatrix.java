package com.server.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "iti_seatmatrix" , schema = "public")
@Data
public class iti_seatmatrix {

    @Column(name = "regid")
    private String regid;

    @Column(name = "name")
    private String name;

    @Column(name = "fname")
    private String fname;

    @Column(name = "addr")
    private String addr;

    @Column(name = "mname")
    private String mname;

    @Column(name = "phno")
    private Long phno ;

    @Column(name = "adarno")
    private String adarno;

    @Column(name = "gender")
    private String gender;

    @Column(name = "caste")
    private String caste;

    @Column(name = "ssc_passed")
    private Boolean ssc_passed;

    @Column(name = "inter_passed")
    private Boolean inter_passed;

    @Column(name = "inter_marks")
    private Integer inter_marks;

    @Column(name = "local")
    private String local;

    @Column(name = "phc")
    private Boolean phc;

    @Column(name = "exservice")
    private Boolean exservice;

    @Column(name = "iti_code" , nullable = false)
    private String iti_code;

    @Id
    @Column(name = "adm_num" , nullable = false)
    private String adm_num;

    @Column(name = "res_category")
    private String res_category ;

    @Column(name = "year_of_admission")
    private String year_of_admission;

    @Column(name = "current_year")
    private Integer current_year;

    @Column(name = "mode_adm")
    private String mode_adm;

    @Column(name = "fromd_training")
    private LocalDate fromd_training;

    @Column(name = "tod_training")
    private LocalDate tod_training;

    @Column(name = "trade_code")
    private Integer trade_code;

    @Column(name = "dist_code" , nullable = false)
    private String dist_code;

    @Column(name = "olddist_code")
    private String olddist_code;

    @Column(name = "olditi_code")
    private String olditi_code;

    @Column(name = "attendance")
    private String attendance;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "idmarks1")
    private String idmarks1;

    @Column(name = "idmarks2")
    private String idmarks2;

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

    @Column(name = "date_of_admission")
    private LocalDate date_of_admission;

    @Column(name = "type_admission")
    private String type_admission;

    @Column(name = "phase")
    private Integer phase;

    @Column(name = "regno")
    private String regno;

    @Column(name = "estcode")
    private String estcode;

    @Column(name = "center_code")
    private String center_code;

    @Column(name = "newadmnum")
    private String newadmnum;

    @Column(name = "final_result")
    private Boolean final_result;

    @Column(name = "final_year_of_pass")
    private String final_year_of_pass;

    @Column(name = "attempt_no")
    private Integer attempt_no;

    @Column(name = "sem_nonsem")
    private String sem_nonsem;

    @Column(name = "dgettrade_code")
    private String dgettrade_code;

    @Column(name = "noof_sems")
    private String noof_sems;

    @Column(name = "finaltrade_code")
    private String finaltrade_code;

    @Column(name = "engg_nonengg")
    private String engg_nonengg;

    @Column(name = "htno")
    private String htno;

    @Column(name = "sem_paper_finalresult")
    private Boolean sem_paper_finalresult;

    @Column(name = "current_sem")
    private String current_sem;

    @Column(name = "oldhtno")
    private String oldhtno;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "est_code")
    private Integer est_code;

    @Column(name = "email_id")
    private String email_id;

    @Column(name = "shift")
    private String shift;

    @Column(name = "unit")
    private String unit;

    @Column(name = "pwd_category")
    private String pwd_category;

    @Column(name = "economic_weaker_section")
    private Boolean economic_weaker_section;

    @Column(name = "is_trainee_dual_mode")
    private Boolean is_trainee_dual_mode;

    @Column(name = "rec_status")
    private String rec_status;

    @Column(name = "error_description")
    private String error_description;

    @Column(name = "state_reg_number")
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