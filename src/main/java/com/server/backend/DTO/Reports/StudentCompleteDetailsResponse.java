package com.server.backend.DTO.Reports;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StudentCompleteDetailsResponse {

    private RegistrationDetail registration;
    private SscMarksDetail sscMarks;
    private List<AppliedIti> appliedItis;
    private VerifiedDetail verified;
    private List<MeritListDetail> meritList;
    private AdmissionDetail admission;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegistrationDetail {
        private String name;
        private String registrationId;
        private String dateOfBirth;
        private String sscHtNo;
        private String fatherName;
        private String motherName;
        private String address;
        private String phoneNo;
        private String gender;
        private String caste;
        private String sscPassed;
        private String phc;
        private String sscPassYear;
        private String registeredPhase;
        private String registrationDate;
        private String verifiedDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SscMarksDetail {
        private String firstLanguage;
        private String secondLanguage;
        private String english;
        private String maths;
        private String science;
        private String social;
        private String total;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppliedIti {
        private String itiCode;
        private String itiName;
        private String phase;
        private String admissionsYear;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifiedDetail {
        private String name;
        private String registrationId;
        private String dateOfBirth;
        private String sscHtNo;
        private String fatherName;
        private String motherName;
        private String address;
        private String phoneNo;
        private String gender;
        private String caste;
        private String sscPassed;
        private String phc;
        private String sscPassYear;
        private String registeredPhase;
        private String registrationDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MeritListDetail {
        private String distName;
        private String itiName;
        private String rank;
        private String phase;
        private String qualification;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdmissionDetail {
        private String district;
        private String iti;
        private String trade;
        private String admissionNumber;
        private String reservationCategory;
        private String yearOfAdmission;
        private String phase;
        private String dateOfAdmission;
        private String phoneNumber;
    }
}