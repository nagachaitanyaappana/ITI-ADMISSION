package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "category_mast", schema = "public")
@Data
public class CategoryMaster {

    @Id
    @Column(name = "category_code", nullable = false, length = 10)
    private String categoryCode;

    @Column(name = "category_order")
    private Integer categoryOrder;
}