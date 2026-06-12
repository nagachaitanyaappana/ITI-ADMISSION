package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "dist_mst")
public class dist_master {

    @Column(name = "statecode", nullable = false)
    private String statecode;

    @Column(name = "dist_code", nullable = false)
    private String distcode;

    @Id
    @Column(name = "dist_name")
    private String distname;

    @Column(name = "itidist_code")
    private String itidistcode;

    @Column(name = "new_dist")
    private String newdist;

    @Column(name = "lgddistcode")
    private String lgddistcode;
}

    
