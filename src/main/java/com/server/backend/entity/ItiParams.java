package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "iti_params", schema = "public")
@Data
public class ItiParams {

    @Id
    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @Column(name = "value", length = 50)
    private String value;
}