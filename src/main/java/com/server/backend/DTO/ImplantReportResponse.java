package com.server.backend.DTO;
import lombok.Data;
import java.time.LocalDate;
@Data
public class ImplantReportResponse {
    private Long implantId;
    private String itiName;
    private String industryName;
    private String facultyName;
    private String tradeName;
    private String industryAddress;
    private Long hrNo;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer noOfDays;
    private Integer noOfStudents;
    private String stateName;
    private String districtName;
    private String location;
    private String description;
}