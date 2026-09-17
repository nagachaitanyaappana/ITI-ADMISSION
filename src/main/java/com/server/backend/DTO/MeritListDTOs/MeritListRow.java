package com.server.backend.DTO.MeritListDTOs;
public record MeritListRow(
    String rank,
    String regid,
    String name,
    String fname,
    String mname,
    String gender,
    String caste,
    String dob,
    String ssc_passed,
    String phc,
    String exservice
) {}
