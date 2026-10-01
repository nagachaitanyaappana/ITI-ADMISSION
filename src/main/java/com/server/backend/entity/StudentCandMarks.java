package com.server.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_cand_marks")
@Data
public class StudentCandMarks {

    @Id
    @Column(name = "regid")
    private String regid;
 
    @Column(name = "ssc_first_lang_marks")
    private Integer sscFirstLangMarks;

    @Column(name = "ssc_first_lang_gpa")
    private String sscFirstLangGpa;

    @Column(name = "ssc_first_lang_grade")
    private String sscFirstLangGrade;

    @Column(name = "ssc_second_lang_marks")
    private Integer sscSecondLangMarks;

    @Column(name = "ssc_second_lang_gpa")
    private String sscSecondLangGpa;

    @Column(name = "ssc_second_lang_grade")
    private String sscSecondLangGrade;

    @Column(name = "ssc_eng_marks")
    private Integer sscEngMarks;

    @Column(name = "ssc_eng_gpa")
    private String sscEngGpa;

    @Column(name = "ssc_eng_grade")
    private String sscEngGrade;

    @Column(name = "ssc_math_marks")
    private Integer sscMathMarks;

    @Column(name = "ssc_math_gpa")
    private String sscMathGpa;

    @Column(name = "ssc_math_grade")
    private String sscMathGrade;

    @Column(name = "ssc_sci_marks")
    private Integer sscSciMarks;

    @Column(name = "ssc_sci_gpa")
    private String sscSciGpa;

    @Column(name = "ssc_sci_grade")
    private String sscSciGrade;

    @Column(name = "ssc_social_marks")
    private Integer sscSocialMarks;

    @Column(name = "ssc_social_gpa")
    private String sscSocialGpa;

    @Column(name = "ssc_social_grade")
    private String sscSocialGrade;

    @Column(name = "inter_first_sub")
    private String interFirstSub;

    @Column(name = "inter_first_marks")
    private Integer interFirstMarks;

    @Column(name = "inter_second_sub")
    private String interSecondSub;

    @Column(name = "inter_second_marks")
    private Integer interSecondMarks;

    @Column(name = "inter_third_sub")
    private String interThirdSub;

    @Column(name = "inter_third_marks")
    private Integer interThirdMarks;

    @Column(name = "ssc_tot_marks")
    private Integer sscTotMarks;

    @Column(name = "ssc_tot_gpa")
    private String sscTotGpa;

    @Column(name = "temp")
    private Integer temp;

    @Column(name = "\"group\"")
    private String group;

    @Column(name = "trno")
    private Integer trno;

    @Column(name = "groupname")
    private String groupName;

    @Column(name = "year")
    private String year;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;
}