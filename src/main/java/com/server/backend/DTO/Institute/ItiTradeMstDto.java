package com.server.backend.DTO.Institute;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class ItiTradeMstDto {
    @NotNull
    private String tradeShort;
    private String tradeName;
    private Integer durationYrs;
    private String engNonengg;
    private String minQual;
    private String tradeFreeze;
    private Integer convApproval;
    private Integer tradeCode;
    private Integer maxInternalMarks;
    private String typeAdmission;
    private String drNondr;
    private Integer unitStrength;
    private Integer displayOrder;
}

    