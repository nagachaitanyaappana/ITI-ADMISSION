package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cand_marks", schema = "public")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class cand_marks {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "regid", nullable = false, length = 15)
    private String regid;

    @Column(name = "ssc_first_lang_marks")
    private Integer ssc_first_lang_marks;

    @Column(name = "ssc_first_lang_gpa")
    private String ssc_first_lang_gpa;

    @Column(name = "ssc_first_lang_grade")
    private String ssc_first_lang_grade;

    @Column(name = "ssc_second_lang_marks")
    private Integer ssc_second_lang_marks;

    @Column(name = "ssc_second_lang_gpa")
    private String ssc_second_lang_gpa;

    @Column(name = "ssc_second_lang_grade")
    private String ssc_second_lang_grade;

    @Column(name = "ssc_eng_marks")
    private Integer ssc_eng_marks;

    @Column(name = "ssc_eng_gpa")
    private String ssc_eng_gpa;

    @Column(name = "ssc_eng_grade")
    private String ssc_eng_grade;

    @Column(name = "ssc_math_marks")
    private Integer ssc_math_marks;

    @Column(name = "ssc_math_gpa")
    private String ssc_math_gpa;

    @Column(name = "ssc_math_grade")
    private String ssc_math_grade;

    @Column(name = "ssc_sci_marks")
    private Integer ssc_sci_marks;

    @Column(name = "ssc_sci_gpa")
    private String ssc_sci_gpa;

    @Column(name = "ssc_sci_grade")
    private String ssc_sci_grade;

    @Column(name = "ssc_social_marks")
    private Integer ssc_social_marks;

    @Column(name = "ssc_social_gpa")
    private String ssc_social_gpa;

    @Column(name = "ssc_social_grade")
    private String ssc_social_grade;

    @Column(name = "inter_first_sub")
    private String inter_first_sub;

    @Column(name = "inter_first_marks")
    private Integer inter_first_marks;

    @Column(name = "inter_second_sub")
    private String inter_second_sub;

    @Column(name = "inter_second_marks")
    private Integer inter_second_marks;

    @Column(name = "inter_third_sub")
    private String inter_third_sub;

    @Column(name = "inter_third_marks")
    private Integer inter_third_marks;

    @Column(name = "ssc_tot_marks")
    private Integer ssc_tot_marks;

    @Column(name = "ssc_tot_gpa")
    private String ssc_tot_gpa;

    @Column(name = "temp", nullable = false, insertable = false, updatable = false)
    private Integer temp;

    @Column(name = "group", length = 50)
    private String groupColumn;

    @Column(name = "trno")
    private Integer trno;

    @Column(name = "groupname", length = 30)
    private String groupname;

    @Column(name = "year", length = 4)
    private String year;

    @Column(name = "regid_exists", length = 1)
    private String regid_exists;

    @Column(name = "regid_exists_a", length = 15)
    private String regid_exists_a;
}
