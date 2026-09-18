package com.server.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IndustryDropdownResponse {

    private Long industryId;
    private String industryName;
    private String industryType;
    private List<TradeResponse> trades;
}