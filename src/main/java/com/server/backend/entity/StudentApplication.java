package com.server.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student_application")
@Data
public class StudentApplication {

    @Id
    @Column(name = "regid")
    private Long regid;

    @Column(name = "ssc_regno")
    private String sscRegno;

    @Column(name = "ssc_board")
    private String sscBoard;

    @Column(name = "ssc_year")
    private String sscYear;

    @Column(name = "ssc_month")
    private String sscMonth;

    @Column(name = "ssc_type")
    private String sscType;
}