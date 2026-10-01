package com.server.backend.DTO;
import lombok.Data;
import java.util.Date;
@Data
public class ImplantReportDTO {

    private Long implantId;
    private Long industryId;
    private String industryName;
    private String facultyName;
    private String tradeShort;
    private String industryAddress;
    private Long hrNo;
    private Date fromDate;
    private Date toDate;
    private Integer noOfDays;
    private Integer noOfStudents;
    private String location;
    private String description;

    public ImplantReportDTO(
            Long implantId,
            Long industryId,
            String industryName,
            String facultyName,
            String tradeShort,
            String industryAddress,
            Long hrNo,
            Date fromDate,
            Date toDate,
            Integer noOfDays,
            Integer noOfStudents,
            String location,
            String description) {

        this.implantId = implantId;
        this.industryId = industryId;
        this.industryName = industryName;
        this.facultyName = facultyName;
        this.tradeShort = tradeShort;
        this.industryAddress = industryAddress;
        this.hrNo = hrNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.noOfDays = noOfDays;
        this.noOfStudents = noOfStudents;
        this.location = location;
        this.description = description;
    }

}