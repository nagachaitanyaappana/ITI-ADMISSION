package com.server.backend.DTO.Institute;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
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
    private Integer maxInternalMarks;
    private String typeAdmission;
    private String drNondr;
    private Integer unitStrength;
    private Integer displayOrder;

}