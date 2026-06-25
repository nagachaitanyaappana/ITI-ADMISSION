package com.server.backend.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StudentRegistrationRequest {
    private String name;
private String fname;
private String mname;
private Long phno;
private String adarno;
private String gender;
private String caste;
private String local;
private Integer pincode;
private String email;
private String addr;
private LocalDate dob;

    private String sscBoard;
    private String hallTicketNumber;
    private String sscYear;
    private String sscMonth;
    private String sscType;
    
}