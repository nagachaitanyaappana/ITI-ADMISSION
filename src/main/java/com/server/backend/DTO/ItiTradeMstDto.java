package com.server.backend.DTO;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
@Data
public class ItiTradeMstDto {
    @NotNull(message="trade code is required")
    private String tradeShort;
    private String tradeName;
    private Integer durationYrs;
    private String engNonengg;
    private String minQual;
    private String tradeFreeze;
    private Integer convApproval;
    private Integer tradeCode;
    /** subject code -> max internal marks; DB column is hstore, not a number. */
    private Map<String, String> maxInternalMarks;
    private String typeAdmission;
    private String drNondr;
    private Integer unitStrength;
    private Integer displayOrder;

}