package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}