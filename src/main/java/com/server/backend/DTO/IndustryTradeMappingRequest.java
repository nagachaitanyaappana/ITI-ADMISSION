package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustryTradeMappingRequest {

    private Integer itiCode;

    private Long industryId;

    private Integer tradeCode;

    private String tradeName;

    private String tradeShort;
}