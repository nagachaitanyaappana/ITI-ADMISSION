package com.server.backend.DTO;



public class candidate_profile_dto {

    private String regId;
    private String rank;
    private String distCode;
    private String qualification;
    private String applicationStatus;

    public candidate_profile_dto() {
    }

    public candidate_profile_dto(String regId, String rank,
            String distCode, String qualification,
            String applicationStatus) {
        this.regId = regId;
        this.rank = rank;
        this.distCode = distCode;
        this.qualification = qualification;
        this.applicationStatus = applicationStatus;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}