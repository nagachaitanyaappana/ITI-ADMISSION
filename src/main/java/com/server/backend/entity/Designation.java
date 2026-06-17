package com.server.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "desig_master", schema = "public")
@Data
public class Designation {

    @Id
    
    @Column(name = "desig_code")
    private String desigCode;

    @Column(name = "designation", length = 50, nullable = false)
    private String designation;

    @Column(name = "display_order")
    private Integer displayOrder;
}