package com.server.backend.DTO;

public class PlacementsDistinctItiResponse {
    private long jobItisCount;
    private long ojItisCount;
    private long apprenticeshipItisCount;
    private long oaItisCount;
    private long higherEducationItisCount;
    private long selfEmploymentItisCount;

    public long getJobItisCount() { return jobItisCount; }
    public void setJobItisCount(long jobItisCount) { this.jobItisCount = jobItisCount; }
    public long getOjItisCount() { return ojItisCount; }
    public void setOjItisCount(long ojItisCount) { this.ojItisCount = ojItisCount; }
    public long getApprenticeshipItisCount() { return apprenticeshipItisCount; }
    public void setApprenticeshipItisCount(long apprenticeshipItisCount) { this.apprenticeshipItisCount = apprenticeshipItisCount; }
    public long getOaItisCount() { return oaItisCount; }
    public void setOaItisCount(long oaItisCount) { this.oaItisCount = oaItisCount; }
    public long getHigherEducationItisCount() { return higherEducationItisCount; }
    public void setHigherEducationItisCount(long higherEducationItisCount) { this.higherEducationItisCount = higherEducationItisCount; }
    public long getSelfEmploymentItisCount() { return selfEmploymentItisCount; }
    public void setSelfEmploymentItisCount(long selfEmploymentItisCount) { this.selfEmploymentItisCount = selfEmploymentItisCount; }
}
