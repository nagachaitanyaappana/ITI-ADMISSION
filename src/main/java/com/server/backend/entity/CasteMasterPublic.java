package com.server.backend.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name="caste_master" , schema="public")
@Data
public class CasteMasterPublic {
    @Id
    @Column(name="caste_code")
    private String casteCode;
    @Column(name="caste_name")
    private String casteName;
    
}
