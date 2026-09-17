package com.server.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_application_regid_seq")
    @SequenceGenerator(
            name = "student_application_regid_seq",
            sequenceName = "student_application_regid_seq",
            allocationSize = 1
    )
    @Column(name = "regid")
    private Integer regid;

    @Column(name = "ssc_regno", nullable = false)
    private String sscRegNo;

    @Column(name = "ssc_board")
    private String sscBoard;

    @Column(name = "ssc_year")
    private String sscYear;

    @Column(name = "ssc_month")
    private String sscMonth;

    @Column(name = "ssc_type")
    private String sscType;

    @Column(name = "year")
    private String year;

    @Column(name = "name")
    private String name;

    @Column(name = "fname")
    private String fname;

    @Column(name = "mname")
    private String mname;

    @Column(name = "gender")
    private String gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "adarno")
    private String adarno;

    @Column(name = "phno")
    private Long phno;

    @Column(name = "email")
    private String email;

    @Column(name = "addr")
    private String addr;

    @Column(name = "pincode")
    private Integer pincode;

    @Column(name = "caste")
    private String caste;

    @Column(name = "sub_caste")
    private String subCaste;

    @Column(name = "local")
    private String local;

    @Column(name = "economic_weaker_section")
    private Boolean economicWeakerSection;

    @Column(name = "phc")
    private Boolean phc;

    @Column(name = "pwd_category")
    private String pwdCategory;

    @Column(name = "exservice")
    private Boolean exservice;

    @Column(name = "ssc_passed")
    private Boolean sscPassed;

    @Column(name = "inter_passed")
    private Boolean interPassed;

    @Column(name = "inter_marks")
    private Integer interMarks;

    @Column(name = "is_trainee_dual_mode")
    private Boolean isTraineeDualMode;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "verified_date")
    private LocalDateTime verifiedDate;

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public void setPwdCategory(String pwdCategory) {
        this.pwdCategory = pwdCategory;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public void setSscPassed(Boolean sscPassed) {
        this.sscPassed = sscPassed;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public String getPwdCategory() {
        return pwdCategory;
    }

    public String getPhase() {
        return phase;
    }

    public Boolean getSscPassed() {
        return sscPassed;
    }

    @Column(name = "trno")
    private Long trno;

    @Column(name = "app_status")
    private String appStatus;

    @Column(name = "data_flag")
    private String dataFlag;

    @Column(name = "reason")
    private String reason;

    @Column(name = "phase")
    private String phase;

    @Column(name = "verified_ip")
    private String verifiedIp;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "ip")
    private String ip;

    @Column(name = "uid")
    private String uid;

    @Column(name = "uidv")
    private String uidv;

    @Column(name = "ewsv")
    private String ewsv;

    @Column(name = "age_21")
    private String age21;

    @Column(name = "experience")
    private String experience;

    @Column(name = "est")
    private String est;

    @Column(name = "ts")
    private String ts;

    @Column(name = "university_area")
    private String universityArea;

    @Column(name = "ssc_eq_marks")
    private String sscEqMarks;

    @Column(name = "study_certificate")
    private String studyCertificate;

    @Column(name = "nativity_certificate")
    private String nativityCertificate;

    @Column(name = "caste_certificate")
    private String casteCertificate;

    @Column(name = "tc")
    private String tc;

    @Column(name = "ph_challenge")
    private String phChallenge;

    @Column(name = "ex_service")
    private String exService;
}