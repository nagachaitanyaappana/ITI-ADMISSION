package com.server.backend.DTO.Reports;

import lombok.AllArgsConstructor; // Add this import
import lombok.Data;
import lombok.NoArgsConstructor; // Add this for safety

@Data
@AllArgsConstructor 
@NoArgsConstructor  
public class TradeDetail {
    private String tradeName;
    private int strength;
}