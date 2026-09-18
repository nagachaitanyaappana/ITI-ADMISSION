package com.server.backend.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 26 - Students Not Admitted.
 * Registered students (public.application) who have no admission record
 * in admissions.iti_admissions for the given year.
 */
@Data
@AllArgsConstructor
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
}
