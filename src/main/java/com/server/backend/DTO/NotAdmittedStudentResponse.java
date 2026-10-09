package com.server.backend.DTO;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 26 - Students Not Admitted.
 * Registered students (public.application) who have no admission record
 * in admissions.iti_admissions for the given year.
 */
@Data
public class NotAdmittedStudentResponse {
    private Long regid;
    private String name;
    private String fname;
    private String gender;
    private String caste;
    private String subCaste;
    private String dob;
    private Long phno;
    private String adarno;
    private String email;
    private String year;
    private String phase;
    private String appStatus;
    private LocalDateTime entryDate;
    private LocalDateTime verifiedDate;

    public NotAdmittedStudentResponse(Long regid, String name, String fname, String gender, String caste,
            String subCaste, String dob, Long phno, String adarno, String email, String year, String phase,
            String appStatus, LocalDateTime entryDate, LocalDateTime verifiedDate) {
        this.regid = regid;
        this.name = name;
        this.fname = fname;
        this.gender = gender;
        this.caste = caste;
        this.subCaste = subCaste;
        this.dob = dob;
        this.phno = phno;
        this.adarno = adarno;
        this.email = email;
        this.year = year;
        this.phase = phase;
        this.appStatus = appStatus;
        this.entryDate = entryDate;
        this.verifiedDate = verifiedDate;
    }
}
