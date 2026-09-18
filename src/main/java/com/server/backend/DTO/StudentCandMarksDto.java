package com.server.backend.DTO;

import lombok.Data;

@Data
public class StudentCandMarksDto {

    private Integer regid;

    private Integer sscFirstLangMarks;
    private String sscFirstLangGpa;
    private String sscFirstLangGrade;

    private Integer sscSecondLangMarks;
    private String sscSecondLangGpa;
    private String sscSecondLangGrade;

    private Integer sscEngMarks;
    private String sscEngGpa;
    private String sscEngGrade;

    private Integer sscMathMarks;
    private String sscMathGpa;
    private String sscMathGrade;

    private Integer sscSciMarks;
    private String sscSciGpa;
    private String sscSciGrade;

    private Integer sscSocialMarks;
    private String sscSocialGpa;
    private String sscSocialGrade;

    private String interFirstSub;
    private Integer interFirstMarks;

    private String interSecondSub;
    private Integer interSecondMarks;

    private String interThirdSub;
    private Integer interThirdMarks;

    private Integer sscTotMarks;
    private String sscTotGpa;

    private Integer temp;
    private String group;
    private Integer trno;
    private String groupName;
    private String year;
}