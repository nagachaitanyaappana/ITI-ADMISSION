package com.server.backend.DTO;

public class PlacementsGroupedResponse {
    private long Job;
    private long OA;
    private long Apprenticeship;
    private long HigherEducation;
    private long SelfEmployment;

    public long getJob() { return Job; }
    public void setJob(long job) { Job = job; }
    public long getOA() { return OA; }
    public void setOA(long OA) { this.OA = OA; }
    public long getApprenticeship() { return Apprenticeship; }
    public void setApprenticeship(long apprenticeship) { Apprenticeship = apprenticeship; }
    public long getHigherEducation() { return HigherEducation; }
    public void setHigherEducation(long higherEducation) { HigherEducation = higherEducation; }
    public long getSelfEmployment() { return SelfEmployment; }
    public void setSelfEmployment(long selfEmployment) { SelfEmployment = selfEmployment; }
}
