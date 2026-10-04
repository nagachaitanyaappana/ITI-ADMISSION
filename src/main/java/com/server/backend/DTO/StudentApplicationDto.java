package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentApplicationDto {

    private Integer regid;

    private String sscRegNo;
    private String sscBoard;
    private String sscYear;
    private String sscMonth;
    private String sscType;
    private String year;

    private String name;
    private String fname;
    private String mname;

    private String gender;
    private LocalDate dob;

    private String adarno;
    private Long phno;
    private String email;

    private String addr;
    private Integer pincode;

    private String caste;
    private String subCaste;
    private String local;

    private Boolean economicWeakerSection;

    private Boolean phc;
    private String pwdCategory;

    // Mirrors StudentApplication.exservice column; transferred via
    // BeanUtils.copyProperties + Jackson (see StudentApplicationServiceImpl),
    // i.e. no direct Java call site. The explicit accessor below counts as a
    // real read/write so the JDT 'unused field' analysis stays satisfied
    // (a bare @SuppressWarnings("unused") is itself flagged as 1102 here).
    private Boolean exservice;

    public Boolean getExservice() {
        return exservice;
    }

    public void setExservice(Boolean exservice) {
        this.exservice = exservice;
    }

    private Boolean sscPassed;
    private Boolean interPassed;
    private Integer interMarks;

    private Boolean isTraineeDualMode;

    private LocalDateTime entryDate;
    private LocalDateTime verifiedDate;

    private Long trno;

    private String appStatus;
    private String dataFlag;
    private String reason;
    private String phase;

    private String verifiedIp;
    private String userId;
    private String ip;

    private String uid;
    private String uidv;
    private String ewsv;

    private String age21;
    private String experience;
    private String est;
    private String ts;

    private String universityArea;

    private String sscEqMarks;

    private String studyCertificate;
    private String nativityCertificate;
    private String casteCertificate;
    private String tc;

    // Mirrors StudentApplication.phChallenge / exService columns; transferred via
    // BeanUtils.copyProperties + Jackson (see StudentApplicationServiceImpl).
    // Explicit accessors below count as a real Java read/write so the
    // JDT 'unused field' analysis stays satisfied.
    private String phChallenge;
    private String exService;

    public String getPhChallenge() {
        return phChallenge;
    }

    public void setPhChallenge(String phChallenge) {
        this.phChallenge = phChallenge;
    }

    public String getExService() {
        return exService;
    }

    public void setExService(String exService) {
        this.exService = exService;
    }
}