package com.server.backend.DTO.Industries;

import lombok.Data;

@Data
public class IndustryConnectedTradesDTO {

    private Long sno;
    private String district;
    private Integer itiCode;
    private String itiName;
    private String tradeName;
    private Integer totalTrainees;
    private String industryName;
}