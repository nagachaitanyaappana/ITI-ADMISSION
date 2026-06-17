package com.server.backend.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentTradeSelId implements Serializable {
    private Integer regid;
    private String itiCode;

}