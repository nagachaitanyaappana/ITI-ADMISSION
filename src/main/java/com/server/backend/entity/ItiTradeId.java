package com.server.backend.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItiTradeId implements Serializable {
    private String itiCode;
    private Integer tradecode;
}