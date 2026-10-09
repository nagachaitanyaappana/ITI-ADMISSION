package com.server.backend.DTO;

import java.util.List;

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

        public RegistrationDetail(String name, String registrationId, String dateOfBirth, String sscHtNo,
                String fatherName, String motherName, String address, String phoneNo, String gender, String caste,
                String sscPassed, String phc, String sscPassYear, String registeredPhase, String registrationDate,
                String verifiedDate) {
            this.name = name;
            this.registrationId = registrationId;
            this.dateOfBirth = dateOfBirth;
            this.sscHtNo = sscHtNo;
            this.fatherName = fatherName;
            this.motherName = motherName;
            this.address = address;
            this.phoneNo = phoneNo;
            this.gender = gender;
            this.caste = caste;
            this.sscPassed = sscPassed;
            this.phc = phc;
            this.sscPassYear = sscPassYear;
            this.registeredPhase = registeredPhase;
            this.registrationDate = registrationDate;
            this.verifiedDate = verifiedDate;
        }
    }

    @Data
    @NoArgsConstructor
    public static class SscMarksDetail {
        private String firstLanguage;
        private String secondLanguage;
        private String english;
        private String maths;
        private String science;
        private String social;
        private String total;

        public SscMarksDetail(String firstLanguage, String secondLanguage, String english, String maths,
                String science, String social, String total) {
            this.firstLanguage = firstLanguage;
            this.secondLanguage = secondLanguage;
            this.english = english;
            this.maths = maths;
            this.science = science;
            this.social = social;
            this.total = total;
        }
    }

    @Data
    @NoArgsConstructor
    public static class AppliedIti {
        private String itiCode;
        private String itiName;
        private String phase;
        private String admissionsYear;

        public AppliedIti(String itiCode, String itiName, String phase, String admissionsYear) {
            this.itiCode = itiCode;
            this.itiName = itiName;
            this.phase = phase;
            this.admissionsYear = admissionsYear;
        }
    }

    @Data
    @NoArgsConstructor
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

        public VerifiedDetail(String name, String registrationId, String dateOfBirth, String sscHtNo,
                String fatherName, String motherName, String address, String phoneNo, String gender, String caste,
                String sscPassed, String phc, String sscPassYear, String registeredPhase, String registrationDate) {
            this.name = name;
            this.registrationId = registrationId;
            this.dateOfBirth = dateOfBirth;
            this.sscHtNo = sscHtNo;
            this.fatherName = fatherName;
            this.motherName = motherName;
            this.address = address;
            this.phoneNo = phoneNo;
            this.gender = gender;
            this.caste = caste;
            this.sscPassed = sscPassed;
            this.phc = phc;
            this.sscPassYear = sscPassYear;
            this.registeredPhase = registeredPhase;
            this.registrationDate = registrationDate;
        }
    }

    @Data
    @NoArgsConstructor
    public static class MeritListDetail {
        private String distName;
        private String itiName;
        private String rank;
        private String phase;
        private String qualification;

        public MeritListDetail(String distName, String itiName, String rank, String phase, String qualification) {
            this.distName = distName;
            this.itiName = itiName;
            this.rank = rank;
            this.phase = phase;
            this.qualification = qualification;
        }
    }

    @Data
    @NoArgsConstructor
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

        public AdmissionDetail(String district, String iti, String trade, String admissionNumber,
                String reservationCategory, String yearOfAdmission, String phase, String dateOfAdmission,
                String phoneNumber) {
            this.district = district;
            this.iti = iti;
            this.trade = trade;
            this.admissionNumber = admissionNumber;
            this.reservationCategory = reservationCategory;
            this.yearOfAdmission = yearOfAdmission;
            this.phase = phase;
            this.dateOfAdmission = dateOfAdmission;
            this.phoneNumber = phoneNumber;
        }
    }
}