package com.server.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "dist_mst", schema = "public")
public class dist_master {

    @Column(name = "statecode", nullable = false , length = 2)
    private String statecode;
    @Id
    @Column(name = "dist_code", nullable = false , length = 4)
    private String distcode;

    
    @Column(name = "dist_name" , length = 25)
    private String distname;

    @Column(name = "itidist_code" , length = 2)
    private String itidistcode;

    @Column(name = "new_dist" , length = 10)
    private String newdist;

    @Column(name = "lgddistcode")
    private String lgddistcode;
}

    
