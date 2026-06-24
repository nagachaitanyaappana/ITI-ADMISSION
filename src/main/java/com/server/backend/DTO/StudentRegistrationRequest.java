package com.server.backend.DTO;

import lombok.Data;

@Data
public class StudentRegistrationRequest {

    private String sscBoard;
    private String hallTicketNumber;
    private String sscYear;
    private String sscMonth;
    private String sscType;
}