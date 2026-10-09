package com.server.backend.DTO;

import lombok.Data;
import java.sql.Timestamp;
@Data
public class IndustryTradeMappingResponse {

    private Long slno;
    private Integer itiCode;
    private Long industryId;
    private String industryName;
    private String industryType;
    private Integer tradeCode;
    private String tradeName;
    private String tradeShort;
    private Timestamp entryTime;

    public IndustryTradeMappingResponse() {
    }

    public IndustryTradeMappingResponse(Long slno, Integer itiCode, Long industryId,
            String industryName, String industryType, Integer tradeCode, String tradeName,
            String tradeShort, Timestamp entryTime) {
        this.slno = slno;
        this.itiCode = itiCode;
        this.industryId = industryId;
        this.industryName = industryName;
        this.industryType = industryType;
        this.tradeCode = tradeCode;
        this.tradeName = tradeName;
        this.tradeShort = tradeShort;
        this.entryTime = entryTime;
    }
}