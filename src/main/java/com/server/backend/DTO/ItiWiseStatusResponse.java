package com.server.backend.DTO;

import lombok.Data;

@Data
public class ItiWiseStatusResponse {
    private String distName;
    private String itiName;
    private String itiCode;
    private int total;
    private int success;
    private int pendingSid;
    private int verified;
    private int toBeVerified;
    private int toBeUpdated;
    private int phoneDuplicateRecords;
    private int aadharDuplicateRecords;
    private int emailDuplicateRecords;

    public ItiWiseStatusResponse(String distName, String itiName, String itiCode, int total, int success,
            int pendingSid, int verified, int toBeVerified, int toBeUpdated, int phoneDuplicateRecords,
            int aadharDuplicateRecords, int emailDuplicateRecords) {
        this.distName = distName;
        this.itiName = itiName;
        this.itiCode = itiCode;
        this.total = total;
        this.success = success;
        this.pendingSid = pendingSid;
        this.verified = verified;
        this.toBeVerified = toBeVerified;
        this.toBeUpdated = toBeUpdated;
        this.phoneDuplicateRecords = phoneDuplicateRecords;
        this.aadharDuplicateRecords = aadharDuplicateRecords;
        this.emailDuplicateRecords = emailDuplicateRecords;
    }
}