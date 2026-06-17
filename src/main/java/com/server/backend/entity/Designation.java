package com.server.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "desig_master")
@Data
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "desig_code")
    private Integer desigCode;

    @Column(name = "designation", length = 50, nullable = false)
    private String designation;

    @Column(name = "display_order")
    private Integer displayOrder;
}